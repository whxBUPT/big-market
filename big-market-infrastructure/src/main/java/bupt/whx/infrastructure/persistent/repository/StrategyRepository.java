package bupt.whx.infrastructure.persistent.repository;

import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import bupt.whx.infrastructure.persistent.dao.IStrategyAwardDao;
import bupt.whx.infrastructure.persistent.po.StrategyAward;
import bupt.whx.infrastructure.persistent.redis.IRedisService;
import bupt.whx.types.common.Constants;
import org.redisson.api.RMap;
import org.springframework.stereotype.Component;
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
 * Description:     策略仓储实现
 *
 * @Author whx
 * @Create 2024/3/11 21:49
 * @Version 1.0
 */
@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        //优先从redis中获取
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if(null!=strategyAwardEntities&&!strategyAwardEntities.isEmpty()){
            return strategyAwardEntities;
        }

        //从库中读取
        List<StrategyAward> strategyAwards=strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities=new ArrayList<>(strategyAwards.size());
        for(StrategyAward strategyAward:strategyAwards){
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }

        //添加到缓存
        redisService.setValue(cacheKey,strategyAwardEntities);

        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTables(Long strategyId, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables) {
        //1.存储抽奖策略范围值
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+strategyId,rateRange);
        //2.存储查找表
        Map<Integer,Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTables);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return  redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+strategyId);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+strategyId,rateKey);
    }
}
