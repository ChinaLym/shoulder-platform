package cn.itlym.shoulder.platform.notify.sms.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author lym
 */
//@Configuration
public class JmsConfig {

    @Bean
    @ConditionalOnMissingBean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate();
    }

}
