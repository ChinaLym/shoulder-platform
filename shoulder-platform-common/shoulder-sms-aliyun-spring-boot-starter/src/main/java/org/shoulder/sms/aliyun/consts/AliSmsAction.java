package org.shoulder.sms.aliyun.consts;

/**
 * 阿里云短信接口 action https://help.aliyun.com/document_detail/102715.html
 *
 * @author lym
 */
public enum AliSmsAction {

    // ======================= 短信发送接口 =======================

    /**
     * 发送短信
     */
    SEND_SMS("SendSms"),

    /**
     * 批量发送短信
     */
    SEND_BATCH_SMS("SendBatchSms"),

    // ======================= 短信查询接口 =======================

    /**
     * 查询短信发送的状态
     */
    QUERY_SEND_DETAILS("QuerySendDetails"),


    // ======================= 签名申请接口 =======================

    /**
     * 申请短信签名
     */
    ADD_SMS_SIGN("AddSmsSign"),

    /**
     * 删除短信签名
     */
    DELETE_SMS_SIGN("DeleteSmsSign"),

    /**
     * 查询短信签名申请状态
     */
    QUERY_SMS_SIGN("QuerySmsSign"),

    /**
     * 修改未审核通过的短信签名，并重新提交审核。
     */
    MODIFY_SMS_SIGN("ModifySmsSign"),

    // ======================= 模板申请接口 =======================

    /**
     * 修改未通过审核的短信模板
     */
    MODIFY_SMS_TEMPLATE("ModifySmsTemplate"),

    /**
     * 查询短信模板的审核状态
     */
    QUERY_SMS_TEMPLATE("QuerySmsTemplate"),

    /**
     * 申请短信模板
     */
    ADD_SMS_TEMPLATE("AddSmsTemplate"),

    /**
     * 删除短信模板
     */
    DELETE_SMS_TEMPLATE("DeleteSmsTemplate"),

    // ======================= 回执消息 =======================

    /**
     * 订阅SmsReport短信状态报告，获取短信发送状态
     */
    SMS_REPORT("SmsReport"),

    /**
     * 订阅SmsUp上行短信消息，获取终端用户回复短信的内容
     */
    SMS_UP("SmsUp"),

    /**
     * 订阅签名审核状态消息（SignSmsReport），获取指定签名的审核状态
     */
    SIGN_SMS_REPORT("SignSmsReport"),

    /**
     * 订阅模板审核状态消息（TemplateSmsReport），获取指定模板的审核状态
     */
    TEMPLATE_SMS_REPORT("TemplateSmsReport"),
    ;


    private final String action;

    AliSmsAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
