package org.architect.wxs;

import org.architect.wxs.loadbalance.ZookeeperLoadBalancer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/7
 * @javadoc ：
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@ServletComponentScan(basePackages = "org.architect.wxs.servlet.gateway")  //默认情况下springboot是不去加载servlet组件的，需要扫描来管理
public class SpringCloudServletGatewayBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudServletGatewayBootstrap.class).run(args);
    }


    @Bean
    public ZookeeperLoadBalancer zookeeperLoadBalancer(DiscoveryClient discoveryClient){
        return new ZookeeperLoadBalancer(discoveryClient);
    }
}
