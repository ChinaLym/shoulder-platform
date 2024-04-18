package cn.itlym.shoulder.platform.gateway.config;

import cn.itlym.shoulder.platform.gateway.ex.ShoulderGatewayException;
import org.shoulder.core.dto.response.BaseResult;
import org.shoulder.core.exception.ErrorCode;
import org.shoulder.core.log.AppLoggers;
import org.shoulder.core.log.Logger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 异常 json 化处理，并返回 shoulder 定义的统一返回值类型
 *
 * @author lym
 */
@Configuration(
        proxyBeanMethods = false
)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static final Logger log = AppLoggers.APP_ERROR;

    private static final String ATTRIBUTE_NAME_HTTP_STATUS = "httpStatus";

    private static final String ATTRIBUTE_NAME_RESPONSE = "shoulderResponseBody";

    /**
     * 默认异常，错误码，500
     */
    private static final int DEFAULT_SERVER_ERROR_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * @see ErrorWebFluxAutoConfiguration#errorWebExceptionHandler
     */
    public JsonExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resourceProperties,
                                ServerProperties serverProperties, ApplicationContext applicationContext,
                                ObjectProvider<ViewResolver> viewResolvers,
                                ServerCodecConfigurer serverCodecConfigurer) {

        super(errorAttributes, resourceProperties, serverProperties.getError(), applicationContext);
        super.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    /**
     * 构建返回的JSON数据格式
     *
     * @param httpStatus http 状态码
     * @param response   如何响应
     * @return {"httpStatus": 500, "shoulderResponseBody": {"code":"xxx", "msg":"xxx", "data":xxx}}
     */
    public static Map<String, Object> buildErrorAttributes(int httpStatus, BaseResult response) {
        Map<String, Object> map = new HashMap<>(2);
        map.put(ATTRIBUTE_NAME_HTTP_STATUS, httpStatus);
        map.put(ATTRIBUTE_NAME_RESPONSE, response);
        return map;
    }

    /**
     * 获取异常属性
     * 为了安全，不打印堆栈信息，即使使用者手动开启
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        int httpStatus = DEFAULT_SERVER_ERROR_HTTP_STATUS;
        Throwable error = super.getError(request);
        if (error instanceof ErrorResponseException) {
            // spring 定义的 http 异常
            httpStatus = ((ResponseStatusException) error).getStatusCode().value();
        } else if (error instanceof ErrorCode) {
            // shoulder 定义的异常
            ErrorCode errorCode = (ErrorCode) error;
            httpStatus = errorCode.getHttpStatusCode().value();
        }
        return buildErrorAttributes(httpStatus, this.buildResponse(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes 错误的属性
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * Render the error information as a JSON payload.
     *
     * @param request the current request
     * @return a {@code Publisher} of the HTTP response
     * @see super#renderErrorResponse
     */
    @NonNull
    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        ErrorAttributeOptions errorAttributeOptions = getErrorAttributeOptions(request, MediaType.ALL);
        Map<String, Object> error = getErrorAttributes(request, errorAttributeOptions);
        return ServerResponse.status(getHttpStatus(error)).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(error));
    }

    /**
     * 根据code获取对应的HttpStatus
     *
     * @param errorAttributes 错误的属性
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (int) errorAttributes.get(ATTRIBUTE_NAME_HTTP_STATUS);
    }

    /**
     * 构建异常信息
     *
     * @param request 请求
     * @param ex      异常
     * @return 异常信息
     */
    protected BaseResult buildResponse(ServerRequest request, Throwable ex) {
        ShoulderGatewayException exception = new ShoulderGatewayException(request, ex);
        log.error(exception);
        return BaseResult.error(exception);
    }
}