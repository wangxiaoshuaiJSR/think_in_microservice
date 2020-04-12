package org.architect.wxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MvcViewApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MvcViewApplication.class);
        springApplication.run(args);
    }
}
