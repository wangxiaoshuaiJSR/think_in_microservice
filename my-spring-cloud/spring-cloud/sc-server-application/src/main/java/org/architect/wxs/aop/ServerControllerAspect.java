package org.architect.wxs.aop;

import org.architect.wxs.annotation.SemaphoreCircuitBreaker;
import org.architect.wxs.annotation.TimeoutCircuitBreaker;
import org.architect.wxs.controller.ServerController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * {@link ServerController} 切面
 */
@Aspect
@Component
public class ServerControllerAspect {

    private ExecutorService executorService = newFixedThreadPool(20);

    private volatile Semaphore semaphore = null;

    @Around("execution(* org.architect.wxs.controller.ServerController.advancedSay(..)) && args(message) ")
    public Object advancedSayInTimeout(ProceedingJoinPoint point, String message) throws Throwable {
        return doInvoke(point, message, 100);
    }

//    @Around("execution(* com.gupao.micro.services.spring.cloud." +
//            "server.controller.ServerController.advancedSay2(..)) && args(message) && @annotation(circuitBreaker)")
//    public Object advancedSay2InTimeout(ProceedingJoinPoint point,
//                                        String message,
//                                        TimeoutCircuitBreaker circuitBreaker) throws Throwable {
//        long timeout = circuitBreaker.timeout();
//        return doInvoke(point, message, timeout);
//    }

    @Around("execution(* org.architect.wxs.controller.ServerController.advancedSay2(..)) && args(message) ")
    public Object advancedSay2InTimeout(ProceedingJoinPoint point,
                                        String message) throws Throwable {

        long timeout = -1;
        if (point instanceof MethodInvocationProceedingJoinPoint) {
            MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
            MethodSignature signature = (MethodSignature) methodPoint.getSignature();
            Method method = signature.getMethod();
            TimeoutCircuitBreaker circuitBreaker = method.getAnnotation(TimeoutCircuitBreaker.class);
            timeout = circuitBreaker.timeout();
        }
        return doInvoke(point, message, timeout);
    }

    @Around("execution(* org.architect.wxs.controller.ServerController.advancedSay3(..))" +
            " && args(message)" +
            " && @annotation(circuitBreaker) ")
    public Object advancedSay3InSemaphore(ProceedingJoinPoint point,
                                          String message,
                                          SemaphoreCircuitBreaker circuitBreaker) throws Throwable {
        int value = circuitBreaker.value();
        if (semaphore == null) {
            semaphore = new Semaphore(value);
        }
        Object returnValue = null;
        try {
            if (semaphore.tryAcquire()) {
                returnValue = point.proceed(new Object[]{message});
                Thread.sleep(1000);
            } else {
                returnValue = errorContent("");
            }
        } finally {
            semaphore.release();
        }

        return returnValue;

    }

    public String errorContent(String message) {
        return "Fault";
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

    private Object doInvoke(ProceedingJoinPoint point,
                            String message, long timeout) throws Throwable {

        Future<Object> future = executorService.submit(() -> {
            Object returnValue = null;
            try {
                returnValue = point.proceed(new Object[]{message});
            } catch (Throwable ex) {
            }
            return returnValue;
        });

        Object returnValue = null;
        try {
            returnValue = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // 取消执行
            returnValue = errorContent("");
        }
        return returnValue;
    }

}
