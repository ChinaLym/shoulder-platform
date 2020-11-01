package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户-角色关系表
 *
 * @author lym
 * @date 2020-11-01 20:57:05
 */

@Data
@NoArgsConstructor
public class UserRelRolePO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;

    /**
     * user_id 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * user_name 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * role_id 角色id
     */
    @TableField("role_id")
    private Long roleId;

}
