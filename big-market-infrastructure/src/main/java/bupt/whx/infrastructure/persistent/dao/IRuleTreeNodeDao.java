package bupt.whx.infrastructure.persistent.dao;


import bupt.whx.infrastructure.persistent.po.RuleTreeNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author
 * @description 规则树节点表DAO
 * @create 2024-02-03 08:43
 */
@Mapper
public interface IRuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

}
