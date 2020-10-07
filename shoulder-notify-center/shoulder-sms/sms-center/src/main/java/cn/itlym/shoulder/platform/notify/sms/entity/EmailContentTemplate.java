package cn.itlym.shoulder.platform.notify.sms.entity;

import java.util.Date;

/**
 * @author lym
 */
public class EmailContentTemplate {

    private String id;

    /**
     * 内容较长
     */
    private String template;

    /**
     * text\html\thymeleaf\freemarker
     */
    private String type;

    /**
     * 参数名，如 "a,b,c"
     */
    private String param;

    private boolean delete;

    private Date createTime;

    private String creator;

}
