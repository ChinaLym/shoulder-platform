package cn.itlym.shoulder.platform.gateway.ex;

import org.shoulder.core.exception.BaseRuntimeException;
import org.shoulder.core.exception.CommonErrorCodeEnum;
import org.shoulder.core.exception.ErrorCode;
import org.springframework.web.reactive.function.server.ServerRequest;

/**
 * 网关异常
 *
 * @author lym
 */
public class ShoulderGatewayException extends BaseRuntimeException {

    public ShoulderGatewayException(ServerRequest request, Throwable ex) {
        super(CommonErrorCodeEnum.UNKNOWN.getCode(), new StringBuilder("Failed to handle request [")
                .append(request.methodName())
                .append(" ")
                .append(request.uri())
                .append("] ")
                .append(ex.getMessage())
                .toString(), ex);
        if (ex instanceof ErrorCode) {
            ErrorCode errorCode = (ErrorCode) ex;
            setHttpStatus(errorCode.getHttpStatusCode());
            setCode(errorCode.getCode());
        }

    }

}
