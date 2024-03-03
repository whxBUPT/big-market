package bupt.whx.infrastructure.persistent.dao;


import bupt.whx.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author
 * @description 抽奖策略奖品明细配置 - 概率、规则 DAO
 * @create 2023-12-16 13:24
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

}
