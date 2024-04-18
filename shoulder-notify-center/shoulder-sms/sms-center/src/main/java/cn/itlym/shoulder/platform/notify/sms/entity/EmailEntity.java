package cn.itlym.shoulder.platform.notify.sms.entity;

import cn.itlym.shoulder.platform.notify.sms.dto.EmailDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

//@Entity
@Table(name = "tb_email")
public class EmailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * 接收人邮箱(多个逗号分开)
     */
    @Column(name = "receive_email", nullable = false, length = 500)
    private String receiveEmail;

    /**
     * 主题
     */
    @Column(name = "subject", nullable = false, length = 100)
    private String subject;

    /**
     * 发送内容
     */
    @Column(name = "content", nullable = false, length = 65535)
    private String content;

    /**
     * 模板
     */
    @Column(name = "template", nullable = false, length = 100)
    private String template;

    /**
     * 发送时间
     */
    @Column(name = "send_time", nullable = false, length = 19)
    private Timestamp sendTime;


    public EmailEntity() {
        super();
    }

    public EmailEntity(EmailDTO mail) {
        this.receiveEmail = Arrays.toString(mail.getReceiver_emails());
        this.subject = mail.getSubject();
        this.content = mail.getContent();
        this.template = mail.getTemplate();
        this.sendTime = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
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

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

}
