package cn.itlym.shoulder.platform.gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * react 工具类
 *
 * @author lym
 */
public class RequestUtil {

    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);

    private static String getBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        log.info("请求体内容;{}", sb.toString());
        return sb.toString();
    }
}
