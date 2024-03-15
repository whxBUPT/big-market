package bupt.whx.domain.strategy.service.armory;

/**
 * ClassName:IStrategyDispatch
 * Package:bupt.whx.domain.strategy.service.armory
 * Description:          策略抽奖调度
 *
 * @Author whx
 * @Create 2024/3/12 9:15
 * @Version 1.0
 */
public interface IStrategyDispatch {

    /**
     * 抽奖
     * @param strategyId
     * @return
     */
    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);

}
