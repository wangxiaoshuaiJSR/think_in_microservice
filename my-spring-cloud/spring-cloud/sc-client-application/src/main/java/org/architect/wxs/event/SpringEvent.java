package org.architect.wxs.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringEvent {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(Object.class);

        // 增加 监听
        context.addApplicationListener(e -> {
            System.err.println("监听 : " + e.getClass().getSimpleName());
        });

        context.refresh();
        context.start();
        context.stop();
        context.close();

    }
}
