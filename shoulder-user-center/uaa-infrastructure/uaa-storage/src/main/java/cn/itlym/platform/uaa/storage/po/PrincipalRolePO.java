package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 凭证-角色关联表
 *
 * @author lym
 * @date 2020-11-01 20:57:04
 */

@Data
@NoArgsConstructor
public class PrincipalRolePO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;

    /**
     * principal_id 凭证标识
     */
    @TableField("principal_id")
    private Long principalId;

    /**
     * principal_type 凭证类型 0：用户，1：部门
     */
    @TableField("principal_type")
    private String principalType;

    /**
     * role_id 角色
     */
    @TableField("role_id")
    private Long roleId;

}
