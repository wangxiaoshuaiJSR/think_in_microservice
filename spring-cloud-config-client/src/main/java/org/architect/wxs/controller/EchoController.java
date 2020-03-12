package org.architect.wxs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/26
 * @javadoc ：
 */
@RestController
@RefreshScope  //动态刷新 配置项
public class EchoController {
    @Value("${my.name}")
    private String name;

    @GetMapping("/get")
    public String get(){
        return name;
    }
}
