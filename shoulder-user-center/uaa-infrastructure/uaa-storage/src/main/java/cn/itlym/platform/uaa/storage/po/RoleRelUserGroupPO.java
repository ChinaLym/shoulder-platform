package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色-用户组的权限关系
 *
 * @author lym
 * @date 2020-11-01 20:57:04
 */

@Data
@NoArgsConstructor
public class RoleRelUserGroupPO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;

    /**
     * role_id 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * user_group_id 目标用户组id
     */
    @TableField("user_group_id")
    private Integer userGroupId;

    /**
     * auth_value 权限值，为资源操作项值之和，0 - 没有权限，1 2 4
     */
    @TableField("auth_value")
    private Long authValue;

}
