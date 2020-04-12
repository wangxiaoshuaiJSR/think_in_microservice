package org.architect.wxs.annotation;

import java.lang.annotation.*;

/**
 * Rest Client 注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestClient {

    /**
     * REST 服务应用名称
     * @return
     */
    String name();
}
