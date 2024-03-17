package bupt.whx.domain.strategy.service;


import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author
 * @description 策略奖品接口
 * @create
 */
public interface IRaffleAward {

    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

}
