package org.architect.wxs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link}
 * 多个 applicationContext 上下文，上下有root 和双亲上下文
 * @author wangxiaoshuai on 2020/4/4
 * @javadoc ：
 */
@EnableAutoConfiguration
@RestController
public class SpringCloudNativeBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.setId("wangxiaoshuai");
        applicationContext.registerBean("hellWorld",String.class,"Hello, World");
        applicationContext.refresh();

        new SpringApplicationBuilder(SpringCloudNativeBootstrap.class)
            .parent(applicationContext)
                .run(args);
    }

    @Autowired
    @Qualifier("hellWorld")
    private String message;

    @GetMapping("/index")
    public String index(){
        return message;
    }
}
