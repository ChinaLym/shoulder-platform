package cn.itlym.platform.uaa.storage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 *
 * @author lym
 * @date 2020-11-01 20:57:04
 */

@Data
@NoArgsConstructor
public class RolePO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField("id")
    private Long id;

    /**
     * name 名称
     */
    @TableField("name")
    private String name;

    /**
     * initials 名称-首字母缩写
     */
    @TableField("initials")
    private String initials;

    /**
     * pinyin 名称-全拼音
     */
    @TableField("pinyin")
    private String pinyin;

    /**
     * type 角色类型，1管理员，2普通角色
     */
    @TableField("type")
    private Integer type;

    /**
     * sub_type 创建的角色类型，用于继承 0操作员 1管理员 2超级管理员
     */
    @TableField("sub_type")
    private Integer subType;

    /**
     * enable 0:禁用，1：启用
     */
    @TableField("enable")
    private Integer enable;

    /**
     * description 描述
     */
    @TableField("description")
    private String description;

    /**
     * creator 创建者
     */
    @TableField("creator")
    private Long creator;

    /**
     * create_time 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * modifier 修改者
     */
    @TableField("modifier")
    private Long modifier;

    /**
     * update_time 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
