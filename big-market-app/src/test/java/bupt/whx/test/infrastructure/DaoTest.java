package bupt.whx.test.infrastructure;

import bupt.whx.infrastructure.persistent.dao.IAwardDao;
import bupt.whx.infrastructure.persistent.dao.IStrategyAwardDao;
import bupt.whx.infrastructure.persistent.po.Award;
import bupt.whx.infrastructure.persistent.po.StrategyAward;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.List;

/**
 * ClassName:DaoTest
 * Package:bupt.whx.test.infrastructure
 * Description:
 *
 * @Author whx
 * @Create 2024/3/3 20:54
 * @Version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {
    @Resource
    private IAwardDao awardDao;
    
    @Test
    public void test(){
        List<Award> awards = awardDao.queryAwardList();

        log.info("测试结果:{}", JSON.toJSONString(awards));
    }

}
