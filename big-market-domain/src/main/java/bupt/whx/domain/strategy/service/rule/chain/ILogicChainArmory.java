package bupt.whx.domain.strategy.service.rule.chain;

/**
 * ClassName:ILogicChainArmory
 * Package:bupt.whx.domain.strategy.service.rule.chain
 * Description:      装配责任链接口
 *
 * @Author whx
 * @Create 2024/3/14 10:14
 * @Version 1.0
 */
public interface ILogicChainArmory {

    ILogicChain appendNext(ILogicChain next);

    ILogicChain next();
}
