package cn.itlym.shoulder.platform.system.star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统中心
 *
 * @author lym
 */
@SpringBootApplication(scanBasePackages = "cn.itlym.shoulder.platform.system")
public class SystemCenter {

    public static void main(String[] args) {
        SpringApplication.run(SystemCenter.class, args);
    }

}
