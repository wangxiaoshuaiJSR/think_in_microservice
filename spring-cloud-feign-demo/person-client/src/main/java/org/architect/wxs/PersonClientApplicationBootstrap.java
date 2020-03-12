package org.architect.wxs;

import org.architect.wxs.rule.LoadBalancerRule;
import org.architect.wxs.service.PersonService;
import org.architect.wxs.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
@SpringBootApplication
@EnableFeignClients(clients = PersonService.class)
@EnableEurekaClient
@EnableHystrix
@Import(WebConfig.class)
//@RibbonClient(value = "person-service",configuration = PersonClientApplicationBootstrap.class) //激活ribbon配置
public class PersonClientApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(PersonClientApplicationBootstrap.class,args);
    }

    @Bean
    public LoadBalancerRule loadBalancerRule(){
        return new LoadBalancerRule();
    }

}
