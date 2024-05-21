package org.shoulder.minio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * minio 配置项
 * minio 使用教程参见官网 https://docs.min.io/cn/java-client-quickstart-guide.html
 *
 * @author lym
 */
@ConfigurationProperties("shoulder.minio")
public class MinioProperties {
    /**
     * 是否开启自动装配
     */
    private Boolean enable = Boolean.TRUE;
    /**
     * minio Server http 地址，注意 minio8 后 endpoint 是 url 而非 host
     */
    private String endpoint = "http://127.0.0.1:9000";
    /**
     * ak 类似于用户ID，用于唯一标识你的账户
     */
    private String accessKey;
    /**
     * sk 账户的密码
     */
    private String secretKey;
    /**
     * 设置该值将覆盖自动发现存储桶region。（可选）
     */
    private String region;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
