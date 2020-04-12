package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/4
 * @javadoc ：
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServerBootstrap.class,args);
    }


    /**
     * 自定义实现外部化配置
     * 将org.springframework.cloud.config.server.config.EnvironmentRepositoryConfiguration.DefaultRepositoryConfiguration失效
     * @return
     */
    @Bean
    public EnvironmentRepository environmentRepository(){
        return (String application, String profile, String label)->{
            Environment environment = new Environment("default",profile);
            List<PropertySource> propertySources = environment.getPropertySources();
            Map<String,Object> source = new HashMap<>();
            source.put("name","王晓帅");
            PropertySource propertySource = new PropertySource("map",source);
            propertySources.add(propertySource);
            return environment;
        };
    }
}
