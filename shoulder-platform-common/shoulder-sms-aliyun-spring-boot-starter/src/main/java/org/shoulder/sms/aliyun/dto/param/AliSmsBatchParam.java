package org.shoulder.sms.aliyun.dto.param;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 阿里云 SMS 短信批量模板.
 * todo OOP
 *
 * @author lym
 */
public class AliSmsBatchParam {

    /**
     * 每条短信的签名
     */
    private List<String> signNames;
    /**
     * 模板id
     */
    private String templateCode;
    /**
     * 每条短信的参数
     */
    private List<Map<String, String>> templateParams;
    /**
     * 手机号
     */
    private List<String> phoneNumbers;

    public AliSmsBatchParam() {
    }

    public AliSmsBatchParam(List<String> signNames, String templateCode, List<Map<String, String>> templateParams, List<String> phoneNumbers) {
        this.signNames = signNames;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.phoneNumbers = phoneNumbers;
    }

    private AliSmsBatchParam(Builder builder) {
        setSignNames(builder.signNames);
        setTemplateCode(builder.templateCode);
        setTemplateParams(builder.templateParams);
        setPhoneNumbers(builder.phoneNumbers);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(AliSmsBatchParam copy) {
        Builder builder = new Builder();
        builder.signNames = copy.getSignNames();
        builder.templateCode = copy.getTemplateCode();
        builder.templateParams = copy.getTemplateParams();
        builder.phoneNumbers = copy.getPhoneNumbers();
        return builder;
    }

    public List<String> getSignNames() {
        return signNames;
    }

    public void setSignNames(List<String> signNames) {
        this.signNames = signNames;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public List<Map<String, String>> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(List<Map<String, String>> templateParams) {
        this.templateParams = templateParams;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }


    public static final class Builder {
        private List<String> signNames;
        private String templateCode;
        private List<Map<String, String>> templateParams;
        private List<String> phoneNumbers;

        private Builder() {
        }

        public Builder signNames(List<String> signNames) {
            this.signNames = signNames;
            return this;
        }

        public Builder templateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }

        public Builder templateParams(List<Map<String, String>> templateParams) {
            this.templateParams = templateParams;
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

        public AliSmsBatchParam build() {
            return new AliSmsBatchParam(this);
        }
    }
}
