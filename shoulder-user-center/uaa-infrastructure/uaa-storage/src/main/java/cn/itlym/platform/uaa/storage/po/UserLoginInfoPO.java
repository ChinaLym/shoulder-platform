package cn.itlym.platform.uaa.storage.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录信息表
 *
 * @author lym
 * @date 2020-10-30 00:10:57
 */
@Data
@NoArgsConstructor
public class UserLoginInfoPO implements Serializable {
    private static final long serialVersionUID = 1L;


    private String id;

    private Long userId;

    private String identifier;

    private Integer identityType;

    private String credential;

    private Date lastPwdModifiedTime;

    private Integer pwdLevel;

    private Integer pwdExpireStrategy;

    private Integer loginStrategyConfig;

    private Integer onlineNumber;

    private String ip;

    private String ipSegment;

    private String mac;

}
