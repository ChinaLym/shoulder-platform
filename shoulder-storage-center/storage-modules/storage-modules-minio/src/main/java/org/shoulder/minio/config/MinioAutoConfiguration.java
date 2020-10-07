package org.shoulder.minio.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio 自动配置
 *
 * @author lym
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(prefix = "shoulder.minio", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MinioAutoConfiguration {

    private final MinioProperties minioProperties;

    public MinioAutoConfiguration(MinioProperties minioProperties) {
        this.minioProperties = minioProperties;
    }

    /**
     * MinioClient Bean
     *
     * @return MinioClient
     * @throws InvalidEndpointException endpoint 错误
     * @throws InvalidPortException     port 错误
     */
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioProperties.getEndpoint(), minioProperties.getPort(),
                minioProperties.getAccessKey(), minioProperties.getSecretKey(),
                minioProperties.getRegion(), minioProperties.isSecure()
        );
    }
}
