package bupt.whx.domain.strategy.service.rule.tree.factory.engine;

import bupt.whx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * ClassName:IDecisionTreeEngine
 * Package:bupt.whx.domain.strategy.service.rule.tree.factory.engine
 * Description:        规则树组合接口
 *
 * @Author whx
 * @Create 2024/3/9 12:55
 * @Version 1.0
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId);
}
