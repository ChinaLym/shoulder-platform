package org.shoulder.sms.aliyun.config;

import org.shoulder.sms.aliyun.client.AliSmsClient;
import org.shoulder.sms.aliyun.client.impl.AliSmsClientImpl;
import org.shoulder.sms.aliyun.properties.AliYunSmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lym
 */
@Configuration(
        proxyBeanMethods = false
)
@EnableConfigurationProperties(value = AliYunSmsProperties.class)
public class AliYunAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliSmsClient aliYunSmsService(AliYunSmsProperties properties) {
        return new AliSmsClientImpl(properties.getAccessKeyId(), properties.getAccessSecret());
    }

}
   