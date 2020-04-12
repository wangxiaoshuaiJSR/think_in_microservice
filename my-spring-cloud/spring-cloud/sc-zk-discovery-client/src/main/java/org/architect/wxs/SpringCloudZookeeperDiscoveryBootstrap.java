package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/4
 * @javadoc ：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZookeeperDiscoveryBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZookeeperDiscoveryBootstrap.class,args);
    }


}
