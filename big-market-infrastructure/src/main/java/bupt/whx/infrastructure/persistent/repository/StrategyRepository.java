package bupt.whx.infrastructure.persistent.repository;

import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;
import bupt.whx.domain.strategy.model.entity.StrategyEntity;
import bupt.whx.domain.strategy.model.entity.StrategyRuleEntity;
import bupt.whx.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import bupt.whx.infrastructure.persistent.dao.IStrategyAwardDao;
import bupt.whx.infrastructure.persistent.dao.IStrategyDao;
import bupt.whx.infrastructure.persistent.dao.IStrategyRuleDao;
import bupt.whx.infrastructure.persistent.po.Strategy;
import bupt.whx.infrastructure.persistent.po.StrategyAward;
import bupt.whx.infrastructure.persistent.po.StrategyRule;
import bupt.whx.infrastructure.persistent.redis.IRedisService;
import bupt.whx.types.common.Constants;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:StrategyRepository
 * Package:bupt.whx.infrastructure.persistent.repository
 * Description:      策略仓储实现
 *
 * @Author whx
 * @Create 2024/3/4 10:23
 * @Version 1.0
 */
@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if(null!=strategyAwardEntities&&strategyAwardEntities.isEmpty()){//缓存中存在才直接返回
            return strategyAwardEntities;
        }
        //从库中读取数据
        List<StrategyAward> strategyAwards=strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());

        for(StrategyAward strategyAward:strategyAwards){
            StrategyAwardEntity strategyAwardEntity=StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }

        redisService.setValue(cacheKey,strategyAwardEntities);

        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTables(String key, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTables);

    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        //优先从缓存中获取
        String cacheKey=Constants.RedisKey.STRATEGY_KEY+strategyId;
        StrategyEntity strategyEntity=redisService.getValue(cacheKey);
        if(null!=strategyEntity){
            return strategyEntity;
        }
        Strategy strategy=strategyDao.queryStrategyByStrategyId(strategyId);

        strategyEntity=StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        redisService.setValue(cacheKey,strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();

    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule=new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(awardId);
        String ruleModels=strategyAwardDao.queryStrategyAwardRuleModels(strategyAward);
        return StrategyAwardRuleModelVO.builder().ruleModels(ruleModels).build();
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId,null,ruleModel);
    }
}
