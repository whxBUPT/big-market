package bupt.whx.test.domain;

import bupt.whx.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName:StrategyArmoryTest
 * Package:bupt.whx.test.domain
 * Description:
 *
 * @Author whx
 * @Create 2024/3/11 22:40
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void test_strategyArmory(){
        strategyArmory.assembleLotteryStrategy(100002L);

    }

    @Test
    public void test_getAssembleRandomVal(){
        for(int i=0;i<100;i++){
            log.info("测试结果:{}",strategyArmory.getRandomAwardId(100002L));
        }


    }
}
