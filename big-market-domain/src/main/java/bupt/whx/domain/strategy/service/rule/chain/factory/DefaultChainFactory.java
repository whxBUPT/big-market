package bupt.whx.domain.strategy.service.rule.chain.factory;


import bupt.whx.domain.strategy.model.entity.StrategyEntity;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import bupt.whx.domain.strategy.service.rule.chain.ILogicChain;

import lombok.*;
import org.springframework.stereotype.Service;


import java.util.Map;

/**
 * ClassName:DefaultChainFactory
 * Package:bupt.whx.domain.strategy.service.rule.chain.factory
 * Description:    默认工厂
 *
 * @Author whx
 * @Create 2024/3/8 21:50
 * @Version 1.0
 */
@Service
public class DefaultChainFactory {

    private final Map<String, ILogicChain> logicChainGroup;


    private final IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository) {
        this.logicChainGroup = logicChainGroup;
        this.repository = repository;
    }

    public ILogicChain openLogicChain(Long strategyId){
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();
        if(null==ruleModels||0==ruleModels.length){
            return logicChainGroup.get("default");
        }
        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current=logicChain;
        for(int i=1;i< ruleModels.length;i++){
            ILogicChain nextChain = logicChainGroup.get(ruleModels[i]);
            current=current.appendNext(nextChain);
        }
        current.appendNext(logicChainGroup.get("default"));
        return logicChain;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }

}
