package bupt.whx.domain.strategy.service.rule.chain;

/**
 * ClassName:ILogicChain
 * Package:bupt.whx.domain.strategy.service.rule.chain
 * Description:      责任链接口
 *
 * @Author whx
 * @Create 2024/3/8 21:02
 * @Version 1.0
 */
public interface ILogicChain extends ILogicChainArmory{
    /**
     *   责任链接口
     * @param userId
     * @param strategyId
     * @return
     */
    Integer logic(String userId,Long strategyId);


}
