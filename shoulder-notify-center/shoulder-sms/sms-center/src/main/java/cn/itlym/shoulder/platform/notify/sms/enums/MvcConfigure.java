package cn.itlym.shoulder.platform.notify.sms.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigure implements WebMvcConfigurer {

    @Autowired
    private MyEnumConverterFactory myEnumConverterFactory;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(myEnumConverterFactory);
    }

}