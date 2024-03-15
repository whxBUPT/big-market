package bupt.whx.domain.strategy.service.rule.chain.impl;

import bupt.whx.domain.strategy.repository.IStrategyRepository;
import bupt.whx.domain.strategy.service.rule.chain.AbstractLogicChain;
import bupt.whx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import bupt.whx.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName:BlackListLogicChain
 * Package:bupt.whx.domain.strategy.service.rule.chain
 * Description:        黑名单责任链
 *
 * @Author whx
 * @Create 2024/3/14 9:38
 * @Version 1.0
 */
@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {
    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-黑名单开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());

        // 查询规则值配置
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 黑名单抽奖判断
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-黑名单接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .build();
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链-黑名单放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);

    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}
