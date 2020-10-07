package cn.itlym.shoulder.platform.gateway.client.dto.param;

import javax.validation.constraints.NotEmpty;

/**
 * @author lym
 */
public class DeleteServiceTokenParam {

    @NotEmpty
    private String st;

    public DeleteServiceTokenParam() {
    }

    public DeleteServiceTokenParam(String st) {
        this.st = st;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}
