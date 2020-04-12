package org.architect.wxs.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.architect.wxs.annotation.SemaphoreCircuitBreaker;
import org.architect.wxs.annotation.TimeoutCircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

@RestController
public class ServerController {

    private final static Random random = new Random();

    @Value("${spring.application.name}")
    private String currentServiceName;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 简易版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/say2")
    public String say2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        // 100 毫秒超时
        String returnValue = null;
        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 超级容错 = 执行错误 或 超时
            returnValue = errorContent(message);
        }
        return returnValue;
    }


    /**
     * 中级版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/middle/say")
    public String middleSay(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        // 100 毫秒超时
        String returnValue = null;

        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // 取消执行
            throw e;
        }
        return returnValue;
    }


    /**
     * 高级版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say")
    public String advancedSay(@RequestParam String message) throws Exception {
        return doSay2(message);
    }

    /**
     * 高级版本 + 注解（超时）
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say2")
    @TimeoutCircuitBreaker(timeout = 100)
    public String advancedSay2(@RequestParam String message) throws Exception {
        return doSay2(message);
    }


    /**
     * 高级版本 + 注解（信号量）
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say3")
    @SemaphoreCircuitBreaker(1)
    public String advancedSay3(@RequestParam String message) throws Exception {
        return doSay2(message);
    }

    private String doSay2(String message) throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);
        System.out.println("say2() costs " + value + " ms.");
        // > 100
        Thread.sleep(value);
        String returnValue = "Say 2 : " + message;
        System.out.println(returnValue);
        return returnValue;
    }

    @HystrixCommand(
            fallbackMethod = "errorContent",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "100")
            }

    )
    @GetMapping("/say")
    public String say(@RequestParam String message) throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);

        System.out.println("say() costs " + value + " ms.");

        // > 100
        Thread.sleep(value);

        System.out.println("ServerController 接收到消息 - say : " + message);
        return "Hello, " + message;
    }

    public String errorContent(String message) {
        return "Fault";
    }


}

