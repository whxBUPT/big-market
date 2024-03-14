package bupt.whx.domain.strategy.service.rule.tree;

import bupt.whx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * ClassName:ILogicTreeNode
 * Package:bupt.whx.domain.strategy.service.rule.tree
 * Description:      规则树接口
 *
 * @Author whx
 * @Create 2024/3/14 19:03
 * @Version 1.0
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);
}
