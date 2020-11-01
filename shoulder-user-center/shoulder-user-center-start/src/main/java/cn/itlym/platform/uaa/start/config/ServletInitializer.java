package cn.itlym.platform.uaa.start.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import cn.itlym.platform.uaa.start.ShoulderApplication;

/**
 * 打成 war 包部署到外部tomcat需要这个，否则删除即可
 *
 * @author shoulder
 */
//@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ShoulderApplication.class);
	}

}
