package org.architect.wxs;

import org.architect.wxs.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@Import(WebConfig.class)
public class PersonServiceProviderApplicationBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceProviderApplicationBootstrap.class,args);
    }
}
