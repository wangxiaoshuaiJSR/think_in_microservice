package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/2
 * @javadoc ：
 */
@EnableTurbine
@SpringBootApplication
@EnableCircuitBreaker //激活@EnableHystrix+springcloud功能
//@EnableHystrix  激活，没有激活springcloud功能   访问路径 /hystrix.stream
public class SpringCloudHystrixBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixBootstrap.class,args);
    }
}
