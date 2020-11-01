package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色-菜单的权限关系
 *
 * @author lym
 * @date 2020-11-01 20:57:04
 */

@Data
@NoArgsConstructor
public class RoleRelMenuPO implements Serializable {
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
     * menu_id 菜单id
     */
    @TableField("menu_id")
    private Integer menuId;

    /**
     * menu_type 菜单类型
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * menu_permission_code 菜单的code
     */
    @TableField("menu_permission_code")
    private String menuPermissionCode;

}
