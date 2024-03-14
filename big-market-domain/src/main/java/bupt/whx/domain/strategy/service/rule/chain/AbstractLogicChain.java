package bupt.whx.domain.strategy.service.rule.chain;

/**
 * ClassName:AbstractLogicChain
 * Package:bupt.whx.domain.strategy.service.rule.chain
 * Description:
 *
 * @Author whx
 * @Create 2024/3/14 9:35
 * @Version 1.0
 */
public abstract class AbstractLogicChain implements ILogicChain{

    private ILogicChain next;

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next=next;
        return next;
    }

    @Override
    public ILogicChain next() {
        return next;
    }

    protected abstract String ruleModel();
}
