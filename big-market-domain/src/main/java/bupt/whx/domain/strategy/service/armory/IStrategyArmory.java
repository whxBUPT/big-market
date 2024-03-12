package bupt.whx.domain.strategy.service.armory;

/**
 * ClassName:IStrategyArmory
 * Package:bupt.whx.domain.strategy.service.armoty
 * Description:  策略装配，负责初始化策略计算
 *
 * @Author whx
 * @Create 2024/3/11 21:44
 * @Version 1.0
 */
public interface IStrategyArmory {

    /**
     * 概率装配
     * @param strategyId
     */
    boolean assembleLotteryStrategy(Long strategyId);


}
