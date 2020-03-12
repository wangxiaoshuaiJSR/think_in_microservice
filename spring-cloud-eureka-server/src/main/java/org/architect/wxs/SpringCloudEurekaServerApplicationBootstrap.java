package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaServerApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServerApplicationBootstrap.class,args);
    }
}
