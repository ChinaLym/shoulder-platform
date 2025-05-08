package cn.itlym.shoulder.platform.db.beecp;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * beecp 数据源自动配置（beecp 官方提供的 starter 有 bug 因为其 provide 属性会导致编译失败）
 *
 * @author lym
 */
//@Configuration(proxyBeanMethods = false)
//@ConditionalOnClass(cn.beecp.BeeDataSource.class)
//@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "cn.beecp.BeeDataSource", matchIfMissing = true)
//public class BeeCpDataSourceAutoConfiguration {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource beeDataSource() throws BeansException {
//        return new cn.beecp.BeeDataSource();
//    }
//}