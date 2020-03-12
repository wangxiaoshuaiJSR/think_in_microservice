package org.architect.wxs;

import org.architect.wxs.health.MyHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@RestController
public class SpringCloudConfigClientApplicationBootstrap {
	private final ContextRefresher contextRefresher;
	private final Environment environment;
	@Autowired
	public SpringCloudConfigClientApplicationBootstrap(ContextRefresher contextRefresher, Environment environment) {
		this.contextRefresher = contextRefresher;
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigClientApplicationBootstrap.class, args);
	}

	@Configuration
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public static class MyPropertySourceLocator implements PropertySourceLocator{

		@Override
		public PropertySource<?> locate(Environment environment) {
			Map<String,Object> source = new HashMap<>();
			source.put("server.port",9090);
			MapPropertySource propertySource = new MapPropertySource("my-property-source",source);
			return propertySource;
		}
	}

	@Scheduled(fixedRate = 5*1000,initialDelay = 1000)
	public void autoRefresh(){
		Set<String> refresh = contextRefresher.refresh();

		refresh.forEach(propertyName->System.out.printf("[Thread: %s] 当前配置已经更新，具体的key " +
				"%s，Value ：%s \n",Thread.currentThread(),propertyName,environment.getProperty(propertyName)));
		System.out.println("刷新了"+refresh);
	}

	@Bean
	public MyHealthIndicator myHealthIndicator(){
		return new MyHealthIndicator();
	}




}