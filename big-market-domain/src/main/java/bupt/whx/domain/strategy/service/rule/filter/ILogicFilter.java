package bupt.whx.domain.strategy.service.rule.filter;

import bupt.whx.domain.strategy.model.entity.RaffleAwardEntity;
import bupt.whx.domain.strategy.model.entity.RuleActionEntity;
import bupt.whx.domain.strategy.model.entity.RuleMatterEntity;

/**
 * ClassName:ILogicFilter
 * Package:bupt.whx.domain.strategy.service.rule
 * Description:  抽奖规则过滤
 *
 * @Author whx
 * @Create 2024/3/12 22:34
 * @Version 1.0
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
