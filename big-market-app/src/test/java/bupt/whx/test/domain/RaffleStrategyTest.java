package bupt.whx.test.domain;


import bupt.whx.domain.strategy.model.entity.RaffleAwardEntity;
import bupt.whx.domain.strategy.model.entity.RaffleFactorEntity;
import bupt.whx.domain.strategy.service.IRaffleStrategy;
import bupt.whx.domain.strategy.service.armory.IStrategyArmory;
import bupt.whx.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import bupt.whx.domain.strategy.service.rule.filter.impl.RuleLockLogicFilter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖策略测试
 * @create 2024-01-06 13:28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private RuleLockLogicFilter ruleLockLogicFilter;

    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));

        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 40500L);
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);

    }



    @Test
    public void test_performRaffle_blacklist() {
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .userId("whx")  // 黑名单用户 user001,user002,user003
                .strategyId(100003L)
                .build();

        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
    }

}
