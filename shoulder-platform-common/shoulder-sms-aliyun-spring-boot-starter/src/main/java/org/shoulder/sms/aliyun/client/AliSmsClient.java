package org.shoulder.sms.aliyun.client;

import org.shoulder.sms.aliyun.dto.param.AliSmsBatchParam;
import org.shoulder.sms.aliyun.dto.param.AliSmsParam;
import org.shoulder.sms.aliyun.exception.AliSmsException;

/**
 * 阿里云短信发送客户端
 *
 * @author lym
 */
public interface AliSmsClient {

    /**
     * 发短信
     *
     * @param aliSmsParam 短信相关参数
     * @return 是否发送成功
     * @throws AliSmsException 发送失败
     */
    boolean send(AliSmsParam aliSmsParam) throws AliSmsException;

    /**
     * 批量发短信
     *
     * @param aliSmsBatchParam 短信相关参数
     * @return 是否发送成功
     * @throws AliSmsException 发送失败
     */
    boolean send(AliSmsBatchParam aliSmsBatchParam) throws AliSmsException;
}
