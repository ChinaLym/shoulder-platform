package cn.itlym.shoulder.generator.model;

import lombok.Data;

import java.util.List;

/**
 * @author lym
 */
@Data
public class TableEntity {

    //表的名称
    private String tableName;
    //表的备注
    private String comments;
    //表的主键
    private ColumnEntity pk;
    //表的列名(不包含主键)
    private List<ColumnEntity> columns;

    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;

    //类名(第一个字母小写)，如：sys_user => sysUser
    private String lowClassName;

    /**
     * Controller 中生成校验权限注解（Spring Security）
     */
    private boolean checkAuth;

    /**
     * Controller 中生成异步批量操作接口。
     */
    private boolean asyncBatch;

    /**
     * 表类型：
     * TREE(parent_id), CREATOR, MODIFIER, CREATOR_TIME, UPDATE_TIME, BATCH
     */
    private List<String> types;

}
