package bupt.whx.domain.strategy.repository;

import bupt.whx.domain.strategy.model.entity.StrategyAwardEntity;
import bupt.whx.domain.strategy.model.entity.StrategyEntity;
import bupt.whx.domain.strategy.model.entity.StrategyRuleEntity;
import bupt.whx.domain.strategy.model.valobj.RuleTreeVO;
import bupt.whx.domain.strategy.model.valobj.StrategyAwardRuleModelVO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:IStrategyRepository
 * Package:bupt.whx.domain.strategy.repository
 * Description:     策略仓储接口
 *
 * @Author whx
 * @Create 2024/3/4 10:21
 * @Version 1.0
 */
public interface IStrategyRepository {
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTables(String key, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    int getRateRange(Long strategyId);

    int getRateRange(String key);

    Integer getStrategyAwardAssemble(String key, int rateKey);



    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);

    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);
}
