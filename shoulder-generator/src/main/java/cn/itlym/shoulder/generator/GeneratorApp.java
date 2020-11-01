package cn.itlym.shoulder.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * 代码生成器，根据数据库表，生成 Entity、RestApi、Controller、Service、ServiceImpl、Repository、Mapper、Mapper.xml、前端视图
 *
 * @author lym
 */

@MapperScan(value = "cn.itlym.shoulder.generator.mapper")
@Configuration
@SpringBootApplication
public class GeneratorApp {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class, args);
    }
}
