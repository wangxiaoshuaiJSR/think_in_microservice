package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/7
 * @javadoc ：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudSluethApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudSluethApplicationBootstrap.class,args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
