package cn.itlym.shoulder.platform.notify.sms.service;

import cn.itlym.shoulder.platform.notify.sms.param.SmsBatchParam;
import cn.itlym.shoulder.platform.notify.sms.param.SmsParam;

public interface SmsService {
    /**
     * 发送短信
     *
     * @param smsParam 参数
     * @return 是否成功
     */
    boolean sendSms(SmsParam smsParam);

    /**
     * 批量发送短信
     *
     * @param smsBatchParam 参数
     */
    boolean sendSms(SmsBatchParam smsBatchParam);
}
