package bupt.whx.domain.strategy.service.armory;

/**
 * ClassName:IStrategyArmory
 * Package:bupt.whx.domain.stratety.service.armory
 * Description:
 *
 * @Author whx
 * @Create 2024/3/4 10:18
 * @Version 1.0
 */
public interface IStrategyArmory {

    void assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long strategyId);

}
