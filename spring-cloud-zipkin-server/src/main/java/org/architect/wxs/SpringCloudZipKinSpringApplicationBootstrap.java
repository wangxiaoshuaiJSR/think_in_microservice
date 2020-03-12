package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/11
 * @javadoc ：
 */
@SpringBootApplication
@EnableZipkinServer
public class SpringCloudZipKinSpringApplicationBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZipKinSpringApplicationBootstrap.class,args);
    }

}
