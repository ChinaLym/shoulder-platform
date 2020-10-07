package cn.itlym.shoulder.platform.gateway.client;

import reactor.core.publisher.Mono;

/**
 * st 相关
 *
 * @author lym
 */
public interface ServiceTokenClient {

    /**
     * 通过 accessToken 获取内部 st
     *
     * @param accessToken Oauth2 accessToken
     * @param appId       接入方应用标识
     * @return serviceToken
     */
    Mono<String> getServiceToken(String accessToken, String appId);


    /**
     * 删除 serviceToken
     *
     * @param serviceToken st
     * @return void
     */
    Mono<Void> deleteServiceToken(String serviceToken);

}
