package bupt.whx.domain.strategy.service;

import bupt.whx.domain.strategy.model.entity.RaffleAwardEntity;
import bupt.whx.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * ClassName:IRaffleStrategy
 * Package:bupt.whx.domain.strategy.service.rule
 * Description:  抽奖策略接口
 *
 * @Author whx
 * @Create 2024/3/5 14:20
 * @Version 1.0
 */
public interface IRaffleStrategy {

    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
