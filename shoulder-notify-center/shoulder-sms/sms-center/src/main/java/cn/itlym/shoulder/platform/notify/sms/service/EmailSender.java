package cn.itlym.shoulder.platform.notify.sms.service;


import cn.itlym.shoulder.platform.notify.sms.dto.EmailDTO;

public interface EmailSender {

    void send(EmailDTO mail) throws Exception;

}
