package bupt.whx.domain.strategy.service.armory;

import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
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
public class StrategyArmory implements IStrategyArmory{
    @Resource
    private IStrategyRepository repository;

    @Override
    public void assembleLotteryStrategy(Long strategyId) {
        //1.查询该策略下的奖品配置
        List<StrategyAwardEntity> strategyAwardEntities= repository.queryStrategyAwardList(strategyId);

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
        repository.storeStrategyAwardSearchRateTables(strategyId,shuffleStrategyAwardSearchRateTables.size(),shuffleStrategyAwardSearchRateTables);

    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange=repository.getRateRange(strategyId);

        return repository.getStrategyAwardAssemble(strategyId,new SecureRandom().nextInt(rateRange));
    }
}
