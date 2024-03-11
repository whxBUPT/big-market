package bupt.whx.domain.strategy.repository;

import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:IStrategyRepository
 * Package:bupt.whx.domain.strategy.repository
 * Description:      策略仓储接口
 *
 * @Author whx
 * @Create 2024/3/11 21:47
 * @Version 1.0
 */
public interface IStrategyRepository {
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTables(Long strategyId, Integer size, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    int getRateRange(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);
}
