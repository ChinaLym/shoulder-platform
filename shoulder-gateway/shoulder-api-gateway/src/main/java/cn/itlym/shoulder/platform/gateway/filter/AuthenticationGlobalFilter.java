package cn.itlym.shoulder.platform.gateway.filter;

import cn.itlym.shoulder.platform.gateway.client.ServiceTokenClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 认证过滤器
 * 请求头是否有 accessToken、如果有，则将他换成内部的 ST（JWT token），请求结束后，将 ST 过期
 *
 * @author lym
 */
@Slf4j
@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {

    /**
     * ST
     */
    public static final String SERVICE_TOKEN_ATTRIBUTE = qualify("serviceToken");
    private static final String ACCESS_TOKEN_IN_HEADER = "Authorization";
    private static final String APP_ID_IN_HEADER = "appId";
    private static final String SERVICE_TOKEN_IN_HEADER = "ST";
    private ServiceTokenClient stClient;
    private List<String> ignorePath;
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public AuthenticationGlobalFilter(ServiceTokenClient stClient, List<String> ignorePath) {
        this.stClient = stClient;
        this.ignorePath = ignorePath;
    }

    /**
     * 按照 spring 的方式来格式化上下文 key 格式
     */
    private static String qualify(String attr) {
        return AuthenticationGlobalFilter.class.getName() + "." + attr;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 开发阶段，debug 模式优化
        if ("true".equals(System.getProperty("permitAll"))) {
            String check = exchange.getRequest().getHeaders().getFirst("check");
            if (StringUtils.isEmpty(check)) {
                return chain.filter(exchange);
            }
        }

        String accessToken = exchange.getRequest().getHeaders().getFirst(ACCESS_TOKEN_IN_HEADER);

        if (StringUtils.isEmpty(accessToken)) {
            String aimPath = exchange.getRequest().getPath().value();
            // ignorePath
            if (ignorePath.contains(aimPath)) {
                log.debug("allow: " + aimPath);
                return chain.filter(exchange);
            }
            // access denied if missing accessToken
            log.info("deny for accessToken is empty: " + aimPath);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // preFilter: add header(ST)

        String appId = exchange.getRequest().getHeaders().getFirst(APP_ID_IN_HEADER);

        return stClient.getServiceToken(accessToken, appId)
                // 请求前，换取 ST
                .map(st -> {
                    exchange.getAttributes().put(SERVICE_TOKEN_ATTRIBUTE, st);
                    ServerHttpRequest request =
                            exchange.getRequest().mutate().header(SERVICE_TOKEN_IN_HEADER, st).build();
                    return exchange.mutate().request(request).build();
                })


                .flatMap(chain::filter)

                // 响应后，删除 ST
                .then(Mono.just(exchange)).map(e -> {
                    String st = (String) exchange.getAttributes().get(SERVICE_TOKEN_ATTRIBUTE);
                    return stClient.deleteServiceToken(st);
                }).then();

    }

    @Override
    public int getOrder() {
        return 0;
    }

}
