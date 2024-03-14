package bupt.whx.domain.strategy.service.rule.tree.impl;

import bupt.whx.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import bupt.whx.domain.strategy.service.rule.tree.ILogicTreeNode;
import bupt.whx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName:RuleLockLogicTreeNode
 * Package:bupt.whx.domain.strategy.service.rule.tree.impl
 * Description:    次数锁节点
 *
 * @Author whx
 * @Create 2024/3/14 19:04
 * @Version 1.0
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {

        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();

    }
}
