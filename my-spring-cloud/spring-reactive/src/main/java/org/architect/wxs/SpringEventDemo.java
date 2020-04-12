package org.architect.wxs;

import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import java.util.concurrent.Executors;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/4/1
 * @javadoc ：
 */
public class SpringEventDemo {

    public static void main(String[] args) {
       /* GenericApplicationContext context = new GenericApplicationContext();
        context.addApplicationListener(event -> {
            System.err.println(Thread.currentThread().getName()+event);
        });

        context.refresh();

        context.publishEvent("Hello,World");

        context.close();*/
        //默认是同步非阻塞
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();

        //切换成异步
        simpleApplicationEventMulticaster.setTaskExecutor(Executors.newSingleThreadExecutor());//注意线程池要关闭 shutdown（）

        simpleApplicationEventMulticaster.addApplicationListener(event -> {
            System.err.println(Thread.currentThread().getName()+event);
        });

        simpleApplicationEventMulticaster.multicastEvent(new PayloadApplicationEvent("hello,world","hello,world"));
    }
}
