package org.shoulder.sms.aliyun.dto.param;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 阿里云 SMS 短信模板.
 *
 * @author lym
 */
public class AliSmsParam {

    /**
     * 签名，如 "LYM"，则短信发送后为 【LYM】 开头，需要与阿里云控制台上的签名对应
     * 查看自己的签名：https://dysms.console.aliyun.com/dysms.htm#/domestic/text/sign
     */
    private String signName;

    /**
     * 模板名称，在阿里云控制台上添加模板时，会有个模板名称
     * https://dysms.console.aliyun.com/dysms.htm#/domestic/text/template
     */
    private String templateCode;

    /**
     * 模板参数，用于填充模板
     */
    private Map<String, String> templateParam;
    /**
     * 手机号
     */
    private List<String> phoneNumbers;

    public AliSmsParam() {
    }

    private AliSmsParam(Builder builder) {
        signName = builder.signName;
        templateCode = builder.templateCode;
        templateParam = builder.templateParam;
        phoneNumbers = builder.phoneNumbers;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(AliSmsParam copy) {
        Builder builder = new Builder();
        builder.signName = copy.getSignName();
        builder.templateCode = copy.getTemplateCode();
        builder.templateParam = copy.getTemplateParam();
        builder.phoneNumbers = copy.getPhoneNumbers();
        return builder;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, String> templateParam) {
        this.templateParam = templateParam;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public static class Builder {
        private String signName;
        private String templateCode;
        private Map<String, String> templateParam;
        private List<String> phoneNumbers;

        private Builder() {
        }

        /**
         * 添加短信模板参数.
         *
         * @param key   the key
         * @param value the value
         * @return this
         */
        public Builder addTemplateParam(final String key, final String value) {
            if (null == this.templateParam) {
                this.templateParam = new HashMap<>(3);
            }

            this.templateParam.put(key, value);
            return this;
        }

        public Builder signName(String signName) {
            this.signName = signName;
            return this;
        }

        public Builder templateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }

        public Builder templateParam(Map<String, String> templateParam) {
            this.templateParam = templateParam;
            return this;
        }

        public Builder phoneNumbers(List<String> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public Builder phoneNumber(String phoneNumbers) {
            if (null == this.phoneNumbers) {
                this.phoneNumbers = new LinkedList<>();
            }
            this.phoneNumbers.add(phoneNumbers);
            return this;
        }

        public AliSmsParam build() {
            return new AliSmsParam(this);
        }
    }


}
