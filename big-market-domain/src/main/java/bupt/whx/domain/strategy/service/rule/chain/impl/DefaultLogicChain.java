package bupt.whx.domain.strategy.service.rule.chain.impl;

import bupt.whx.domain.strategy.service.armory.IStrategyDispatch;
import bupt.whx.domain.strategy.service.rule.chain.AbstractLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName:DefaultLogicChain
 * Package:bupt.whx.domain.strategy.service.rule.chain.impl
 * Description:         兜底的责任链
 *
 * @Author whx
 * @Create 2024/3/8 21:10
 * @Version 1.0
 */
@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {
    @Resource
    protected IStrategyDispatch strategyDispatch;

    @Override
    public Integer logic(String userId, Long strategyId) {
        Integer awardId=strategyDispatch.getRandomAwardId(strategyId);
        log.info("责任链-默认处理 userId:{}  strategyId:{}  ruleModel:{}  awardId:{}",userId,strategyId,ruleModel(),awardId);
        return awardId;
    }

    @Override
    protected String ruleModel() {
        return "default";
    }
}
