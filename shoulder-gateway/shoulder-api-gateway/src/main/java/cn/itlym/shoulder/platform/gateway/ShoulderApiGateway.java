package cn.itlym.shoulder.platform.gateway;

import org.shoulder.core.dto.response.BaseResult;
import org.shoulder.core.exception.CommonErrorCodeEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * 网关启动类
 *
 * @author lym
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ShoulderApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(ShoulderApiGateway.class, args);
    }

    @RequestMapping("/fallback")
    public Mono<BaseResult> fallback() {
        return Mono.just(BaseResult.error(CommonErrorCodeEnum.SERVICE_RESPONSE_TIMEOUT));
    }
}
