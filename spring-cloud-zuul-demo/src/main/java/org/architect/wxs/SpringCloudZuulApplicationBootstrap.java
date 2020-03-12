package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class SpringCloudZuulApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulApplicationBootstrap.class,args);
    }
}
