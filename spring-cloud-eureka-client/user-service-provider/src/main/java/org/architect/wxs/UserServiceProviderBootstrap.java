package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceProviderBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceProviderBootstrap.class,args);
    }
}
