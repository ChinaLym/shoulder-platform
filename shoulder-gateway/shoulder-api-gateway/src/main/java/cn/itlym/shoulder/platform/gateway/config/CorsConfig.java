package cn.itlym.shoulder.platform.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 处理跨域请求
 * 简单跨域: GET，HEAD、以及部分 POST请求（请求头的"Content-Type"为 application/x-www-form-urlencoded、multipart/form-data、text/plain
 * 其他跨域请求会在实际发送前进行一次 OPTIONS 探测请求
 *
 * @author lym
 */
@Configuration(
        proxyBeanMethods = false
)
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        final String all = "*";

        CorsConfiguration config = new CorsConfiguration();
        // 是否允许请求带有验证信息 cookie跨域
        config.setAllowCredentials(Boolean.TRUE);
        // 允许访问的客户端域名
        config.addAllowedOrigin(all);
        // 允许服务端访问的客户端请求头
        config.addAllowedHeader(all);
        // 允许访问的方法名,GET POST等
        config.addAllowedMethod(all);
        // 允许前端js访问自定义响应头
        //config.addExposedHeader("setToken");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }


}