package bupt.whx.domain.strategy.service.rule.tree.impl;

import bupt.whx.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import bupt.whx.domain.strategy.service.rule.tree.ILogicTreeNode;
import bupt.whx.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ClassName:RuleLuckAwardLogicTreeNode
 * Package:bupt.whx.domain.strategy.service.rule.tree.impl
 * Description:         兜底奖励节点
 *
 * @Author whx
 * @Create 2024/3/9 12:49
 * @Version 1.0
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(DefaultTreeFactory.StrategyAwardData.builder().awardId(101).awardRuleValue("1,100").build())
                .build();
    }
}
