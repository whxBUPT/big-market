package bupt.whx.test.infrastructure;

import bupt.whx.domain.strategy.model.valobj.RuleTreeVO;
import bupt.whx.domain.strategy.repository.IStrategyRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName:StrategyRepositoryTest
 * Package:bupt.whx.test.infrastructure
 * Description:     仓库数据查询
 *
 * @Author whx
 * @Create 2024/3/9 23:15
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {
    @Resource
    private IStrategyRepository strategyRepository;

    @Test
    public void queryRuleTreeVOByTreeId(){
        RuleTreeVO ruleTreeVO=strategyRepository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果:{}", JSON.toJSONString(ruleTreeVO));
    }

}
