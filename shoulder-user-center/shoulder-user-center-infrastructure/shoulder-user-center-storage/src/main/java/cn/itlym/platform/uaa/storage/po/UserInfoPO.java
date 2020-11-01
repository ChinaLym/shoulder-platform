package cn.itlym.platform.uaa.storage.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author lym
 * @date 2020-10-30 00:10:57
 */
@Data
@NoArgsConstructor
public class UserInfoPO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String name;

    private Integer sex;

    private Integer age;

    private Date birth;

    private Integer level;

    private String idCard;

    private String realName;

    private String initials;

    private String spellings;

    private String phoneNum;

    private String email;

    private Integer status;

    private Integer groupAuth;

    private Integer groupId;

    private String groupName;

    private String groupPath;

    private Long creator;

    private Date createTime;

    private Date updateTime;

    private String description;

}
