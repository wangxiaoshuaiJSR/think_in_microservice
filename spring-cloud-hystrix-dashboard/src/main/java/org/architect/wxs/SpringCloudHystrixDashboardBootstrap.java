package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/2
 * @javadoc ：
 */
@SpringBootApplication
@EnableHystrixDashboard
public class SpringCloudHystrixDashboardBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixDashboardBootstrap.class,args);
    }
}
