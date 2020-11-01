package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户组、部门表
 *
 * @author lym
 * @date 2020-10-30 00:10:57
 */
@Data
@NoArgsConstructor
@TableName("c_auth_application")
public class UserGroupPO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;

    private String name;

    private String initials;

    private String spellings;

    private Long parentId;

    private Integer level;

    private String path;

    private String description;

    private Integer weight;

    private Integer displayOrder;

    private Long creator;

    private Date createTime;

}
