package cn.itlym.shoulder.platform.notify.sms;

import cn.itlym.shoulder.platform.notify.sms.enums.EnumDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 微服务的短信中心
 *
 * @author lym
 */
@SpringBootApplication
public class SmsCenterStarter {

    public static void main(String[] args) {
        SpringApplication.run(SmsCenterStarter.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Enum.class, new EnumDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
