package cn.itlym.platform.uaa.storage.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录失败记录表
 *
 * @author lym
 * @date 2020-10-30 00:10:57
 */
@Data
@NoArgsConstructor
public class UserLoginRecordFailPO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String ip;

    private String mac;

    private String address;

    private String identifier;

    private Integer identityType;

    private Integer failTimes;

    private Date unlockTime;

    private Date loginTime;

}
