package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色-资源类型的权限关系
 *
 * @author lym
 * @date 2020-11-01 20:57:04
 */

@Data
@NoArgsConstructor
public class RoleRelResourceTypePO implements Serializable {
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
     * contain_sub 是否包含下级：0 - 不包含； 1 - 包含
     */
    @TableField("contain_sub")
    private Integer containSub;

    /**
     * resource_type 资源类型编码
     */
    @TableField("resource_type")
    private String resourceType;

    /**
     * auth_value
     */
    @TableField("auth_value")
    private Long authValue;

}
