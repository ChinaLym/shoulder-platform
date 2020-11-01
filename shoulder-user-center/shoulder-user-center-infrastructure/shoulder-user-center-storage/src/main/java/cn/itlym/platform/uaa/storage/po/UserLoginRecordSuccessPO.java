package cn.itlym.platform.uaa.storage.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录成功记录表
 *
 * @author lym
 * @date 2020-10-30 00:10:57
 */

@Data
@NoArgsConstructor
public class UserLoginRecordSuccessPO implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    private String ip;

    private Integer identityType;

    private String identifier;

    private Date loginTime;

}
