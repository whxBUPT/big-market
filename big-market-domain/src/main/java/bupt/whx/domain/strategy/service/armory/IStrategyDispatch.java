package bupt.whx.domain.strategy.service.armory;

/**
 * ClassName:IStrategyDispatch
 * Package:bupt.whx.domain.strategy.service.armory
 * Description:        策略抽奖调度
 *
 * @Author whx
 * @Create 2024/3/5 9:44
 * @Version 1.0
 */
public interface IStrategyDispatch {


    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);
}
