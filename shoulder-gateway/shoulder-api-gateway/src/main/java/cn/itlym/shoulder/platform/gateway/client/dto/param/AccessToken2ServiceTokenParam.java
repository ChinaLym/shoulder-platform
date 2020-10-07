package cn.itlym.shoulder.platform.gateway.client.dto.param;

import javax.validation.constraints.NotEmpty;

/**
 * @author lym
 */
public class AccessToken2ServiceTokenParam {

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String appId;

    public AccessToken2ServiceTokenParam() {
    }

    public AccessToken2ServiceTokenParam(String accessToken, String appId) {
        this.accessToken = accessToken;
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
