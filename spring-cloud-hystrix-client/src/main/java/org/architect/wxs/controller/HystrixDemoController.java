package org.architect.wxs.controller;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/2
 * @javadoc ：
 */
@RestController
public class HystrixDemoController {
    private final Random random = new Random();

    @GetMapping("/helloWorld2")
    public String helloWorld2() throws InterruptedException {
        return new HelloWorldCommand().execute();
    }

    private class HelloWorldCommand extends com.netflix.hystrix.HystrixCommand<String>{

        protected HelloWorldCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("HelloWorld"),100);
        }

        @Override
        protected String run() throws Exception {
            Random random = new Random();
            int value = random.nextInt(200);
            System.out.println(value);
            if(value>100){
                throw new RuntimeException("超时了！！！");
            }
            return "hello world";
        }

        protected String getFallback(){
            return HystrixDemoController.this.errorContent();
        }
    }


    @GetMapping("/helloWorld")
    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100")
            }
    )
    public String helloWorld() throws InterruptedException {
        Thread.sleep(random.nextInt(200));
        return "success";
    }

    public String errorContent(){
        return "error";
    }
}
