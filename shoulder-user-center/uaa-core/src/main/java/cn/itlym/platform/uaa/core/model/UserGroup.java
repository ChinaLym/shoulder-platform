package cn.itlym.platform.uaa.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户组、部门表
 *
 * @author lym
 */
@Data
@NoArgsConstructor
public class UserGroup implements Serializable {
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
