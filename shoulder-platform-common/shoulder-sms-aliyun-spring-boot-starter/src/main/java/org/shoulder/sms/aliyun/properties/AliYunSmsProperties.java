package org.shoulder.sms.aliyun.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云短信相关配置
 *
 * @author lym
 */
@ConfigurationProperties(prefix = "ali-cloud")
public class AliYunSmsProperties {

    /**
     * 阿里 accessKeyId
     */
    private String accessKeyId;
    /**
     * 阿里 accessSecret
     */
    private String accessSecret;

    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private String product = "Dysmsapi";

    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private String domain = "dysmsapi.aliyuncs.com";
    /**
     * regionId
     */
    private String regionId = "cn-hangzhou";

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}