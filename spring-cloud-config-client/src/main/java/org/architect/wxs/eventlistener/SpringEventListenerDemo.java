package org.architect.wxs.eventlistener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/23
 * @javadoc ：spring 事件监听机制
 */
public class SpringEventListenerDemo {

    public static void main(String[] args) {
        //注解驱动spring的上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册监听器
        applicationContext.addApplicationListener(new ApplicationListener<MyApplicationEvent>() {
            //监听到的事件
            @Override
            public void onApplicationEvent(MyApplicationEvent applicationEvent) {
                System.out.println(applicationEvent.getSource()+""+applicationEvent.getApplicationContext());
            }
        });

        applicationContext.refresh();

        //发布事件
        applicationContext.publishEvent(new MyApplicationEvent(applicationContext,"hello,world"));
        applicationContext.publishEvent(new MyApplicationEvent(applicationContext,"wangxiaoshuai"));

    }

    private static class MyApplicationEvent extends ApplicationEvent{

        private final ApplicationContext applicationContext;


        public MyApplicationEvent(ApplicationContext applicationContext,Object source) {
            super(source);
            this.applicationContext = applicationContext;
        }

        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }
    }
}
