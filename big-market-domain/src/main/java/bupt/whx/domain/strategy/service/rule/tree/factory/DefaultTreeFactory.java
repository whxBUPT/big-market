package bupt.whx.domain.strategy.service.rule.tree.factory;

import bupt.whx.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import bupt.whx.domain.strategy.model.valobj.RuleTreeVO;
import bupt.whx.domain.strategy.service.rule.tree.ILogicTreeNode;
import bupt.whx.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import bupt.whx.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ClassName:DefaultTreeFactory
 * Package:bupt.whx.domain.strategy.service.rule.tree.factory
 * Description:     规则树工厂
 *
 * @Author whx
 * @Create 2024/3/9 12:52
 * @Version 1.0
 */
@Service
public class DefaultTreeFactory {


    private final Map<String,ILogicTreeNode> logicTreeNodeGroup;


    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO){
        return new DecisionTreeEngine(logicTreeNodeGroup,ruleTreeVO);
    }

    /**
     * 决策树个动作实习
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }

}
