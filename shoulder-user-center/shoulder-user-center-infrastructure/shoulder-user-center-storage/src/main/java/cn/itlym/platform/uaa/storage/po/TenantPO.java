package cn.itlym.platform.uaa.storage.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 租户信息
 *
 * @author lym
 * @date 2020-10-30 00:10:57
 */

@Data
@NoArgsConstructor
public class TenantPO implements Serializable {
    private static final long serialVersionUID = 1L;


    private String tenantId;

    private String tenantCode;

    private String name;

    private String desc;

    private String logoUrl;

    private Integer status;

    private Long creator;

    private Date createTime;

    private Long modifier;

    private Date updateTime;

    private String province;

    private String city;

    private String district;

    private String address;

    private String linkMan;

    private String linkPhone;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String adcode;

}
