package cn.itlym.shoulder.platform.notify.sms.service;


import cn.itlym.shoulder.platform.notify.sms.dto.EmailDTO;
import cn.itlym.shoulder.platform.notify.sms.dto.UiResult;

public interface EmailService {
    /**
     * 纯文本
     */
    void send(EmailDTO mail) throws Exception;

    /**
     * 富文本
     */
    void sendHtml(EmailDTO mail) throws Exception;

    /**
     * 模版发送 freemarker
     */
    void sendFreemarker(EmailDTO mail) throws Exception;

    /**
     * 模版发送 thymeleaf(弃用、需要配合模板)
     */
    void sendThymeleaf(EmailDTO mail) throws Exception;

    /**
     * 队列
     */
    void sendQueue(EmailDTO mail) throws Exception;


    UiResult listMail(EmailDTO mail);
}
