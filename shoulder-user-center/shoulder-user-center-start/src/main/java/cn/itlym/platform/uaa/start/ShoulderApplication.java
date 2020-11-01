package cn.itlym.platform.uaa.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * uaa 启动类
 *
 * @author shoulder
 */
@SpringBootApplication(scanBasePackages = "cn.itlym.platform.uaa")
public class ShoulderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoulderApplication.class, args);
    }

}
