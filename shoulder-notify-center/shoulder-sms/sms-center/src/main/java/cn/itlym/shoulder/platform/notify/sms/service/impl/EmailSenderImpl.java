package cn.itlym.shoulder.platform.notify.sms.service.impl;

import cn.itlym.shoulder.platform.notify.sms.dto.EmailDTO;
import cn.itlym.shoulder.platform.notify.sms.service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderImpl implements EmailSender {

    static {
        // 避免在linux下利用javamail1.4.4发邮件带附件，附件名过长而被被截断，导致接收端解析失败的异常
        System.setProperty("mail.mime.splitlongparameters", "false");
    }

    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    @Autowired
    private JavaMailSender mailSender;//执行者

    @Override
    public void send(EmailDTO mail) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USER_NAME);
        message.setTo(mail.getReceiver_emails());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailSender.send(message);
    }

}