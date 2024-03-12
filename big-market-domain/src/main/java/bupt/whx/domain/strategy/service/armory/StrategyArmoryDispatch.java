package bupt.whx.domain.strategy.service.armory;

import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;
import bupt.whx.domain.strategy.model.entity.StrategyEntity;
import bupt.whx.domain.strategy.model.entity.StrategyRuleEntity;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import bupt.whx.types.enums.ResponseCode;
import bupt.whx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.sql.Array;
import java.util.*;

/**
 * ClassName:StrategyArmory
 * Package:bupt.whx.domain.strategy.service.armory
 * Description:   策略装配，负责初始化策略计算
 *
 * @Author whx
 * @Create 2024/3/11 21:46
 * @Version 1.0
 */
@Service
@Slf4j
public class StrategyArmoryDispatch implements IStrategyArmory,IStrategyDispatch{
    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1.查询该策略下的奖品配置
        List<StrategyAwardEntity> strategyAwardEntities= repository.queryStrategyAwardList(strategyId);
        assembleLotteryStrategy(String.valueOf(strategyId),strategyAwardEntities);

        //2.权重配置——适用于rule_weight权重规则
        StrategyEntity strategyEntity=repository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight=strategyEntity.getRuleWeight();
        if(null==ruleWeight){
            return true;
        }
        StrategyRuleEntity strategyRuleEntity=repository.queryStrategyRule(strategyId,ruleWeight);
        if (null == strategyRuleEntity) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }

        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        for(String key:keys){
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity->!ruleWeightValues.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key),strategyAwardEntitiesClone);
        }

        return true;

    }

    /**
     * 装配抽奖策略
     * @param key
     * @param strategyAwardEntities
     */
    private void assembleLotteryStrategy(String key,List<StrategyAwardEntity> strategyAwardEntities){
        //2.获取最小的概率值
        BigDecimal minAwardRate=strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        //3.获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //4.用1/0.0001获取概率范围
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        //5.生成查找表
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for(StrategyAwardEntity strategyAward:strategyAwardEntities){
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();
            //计算每个概率要占多少位置，循环填充
            for(int i=0;i<rateRange.multiply(awardRate).setScale(0,RoundingMode.CEILING).intValue();i++){
                strategyAwardSearchRateTables.add(awardId);
            }
        }
        //6.将查找表乱序
        Collections.shuffle(strategyAwardSearchRateTables);

        //7.将查找表存入map
        HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables = new HashMap<>();
        for(int i=0;i<strategyAwardSearchRateTables.size();i++){
            shuffleStrategyAwardSearchRateTables.put(i,strategyAwardSearchRateTables.get(i));
        }

        //8.存储到redis
        repository.storeStrategyAwardSearchRateTables(key,shuffleStrategyAwardSearchRateTables.size(),shuffleStrategyAwardSearchRateTables);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange=repository.getRateRange(strategyId);

        return repository.getStrategyAwardAssemble(String.valueOf(strategyId),new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key=String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        int rateRange=repository.getRateRange(key);
        return repository.getStrategyAwardAssemble(key,new SecureRandom().nextInt(rateRange));
    }
}
