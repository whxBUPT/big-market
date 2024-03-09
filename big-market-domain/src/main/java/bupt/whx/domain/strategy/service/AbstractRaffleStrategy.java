package bupt.whx.domain.strategy.service;

import bupt.whx.domain.strategy.model.entity.RaffleAwardEntity;
import bupt.whx.domain.strategy.model.entity.RaffleFactorEntity;
import bupt.whx.domain.strategy.model.entity.RuleActionEntity;
import bupt.whx.domain.strategy.model.entity.StrategyEntity;
import bupt.whx.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import bupt.whx.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import bupt.whx.domain.strategy.service.IRaffleStrategy;
import bupt.whx.domain.strategy.service.armory.IStrategyDispatch;
import bupt.whx.domain.strategy.service.rule.chain.ILogicChain;
import bupt.whx.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import bupt.whx.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import bupt.whx.types.enums.ResponseCode;
import bupt.whx.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName:AbstractRaffleStrategy
 * Package:bupt.whx.domain.strategy.service.raffle
 * Description:   抽奖策略抽象类
 *
 * @Author whx
 * @Create 2024/3/5 14:34
 * @Version 1.0
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    private DefaultChainFactory defaultChainFactory;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
    }

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }


    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        //1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if(null==strategyId|| StringUtils.isBlank(userId)){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        //2.责任链处理抽奖
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        Integer awardId = logicChain.logic(userId, strategyId);

        // 3. 查询奖品规则「抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）」
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);

        //4.抽奖中——规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).awardId(awardId).build(),
                strategyAwardRuleModelVO.raffleCenterRuleModelList());

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionCenterEntity.getCode())){
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();
        }



        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();

    }
    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}