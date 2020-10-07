package cn.itlym.shoulder.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 启动类
 *
 * @author lym
 */
@Configuration
@SpringBootApplication
public class GeneratorApp {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class, args);
    }
}
