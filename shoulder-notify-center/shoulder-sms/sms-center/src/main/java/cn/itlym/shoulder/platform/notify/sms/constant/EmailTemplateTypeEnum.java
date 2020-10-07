package cn.itlym.shoulder.platform.notify.sms.constant;

/**
 * @author lym
 */
public enum EmailTemplateTypeEnum {

    /**
     * 纯文本
     */
    TEXT(1),

    /**
     * 网页
     */
    HTML(2),

    /**
     * themeleaf
     */
    THYMELEAF(3),

    /**
     * freeMarker
     */
    FREEMARKER(4),

    ;

    private int value;

    EmailTemplateTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
