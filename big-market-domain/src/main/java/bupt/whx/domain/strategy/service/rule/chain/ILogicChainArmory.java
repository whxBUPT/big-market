package bupt.whx.domain.strategy.service.rule.chain;

/**
 * ClassName:ILogicChainArmory
 * Package:bupt.whx.domain.strategy.service.rule.chain
 * Description:       装配接口
 *
 * @Author whx
 * @Create 2024/3/8 22:04
 * @Version 1.0
 */
public interface ILogicChainArmory {

    ILogicChain appendNext(ILogicChain next);

    ILogicChain next();
}
