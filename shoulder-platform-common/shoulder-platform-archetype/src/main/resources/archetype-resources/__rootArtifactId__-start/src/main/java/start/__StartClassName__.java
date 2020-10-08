#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ${appId} 启动类
 *
 * @author ${author}
 */
@SpringBootApplication(scanBasePackages = "${package}")
public class ${StartClassName} {

    public static void main(String[] args) {
        SpringApplication.run(${StartClassName}.class, args);
    }

}
