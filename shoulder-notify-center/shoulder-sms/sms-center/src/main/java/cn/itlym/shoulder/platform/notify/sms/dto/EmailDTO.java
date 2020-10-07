package cn.itlym.shoulder.platform.notify.sms.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * EmailDTO
 */
public class EmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //必填参数
    private String[] receiver_emails;//接收方邮件
    private String subject;//主题
    private String content;//邮件内容
    //选填
    private String template;//模板
    private HashMap<String, String> templateParam;// 自定义参数


    public EmailDTO() {
        super();
    }

    public EmailDTO(String[] receiver_emails, String subject, String content, String template,
                    HashMap<String, String> templateParam) {
        super();
        this.receiver_emails = receiver_emails;
        this.subject = subject;
        this.content = content;
        this.template = template;
        this.templateParam = templateParam;
    }


    public String[] getReceiver_emails() {
        return receiver_emails;
    }

    public void setReceiver_emails(String[] receiver_emails) {
        this.receiver_emails = receiver_emails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public HashMap<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(HashMap<String, String> templateParam) {
        this.templateParam = templateParam;
    }
}
