package org.architect.wxs.web.controller;

import org.architect.wxs.domain.User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/2/23
 * @javadoc ：
 */
@RestController
public class UserController {
    @PostMapping("/save")
    public User save(@Valid @RequestBody User user){
        return user;
    }

    @PostMapping("/save2")
    public User save2( @RequestBody User user){
        //spring API 校验
        Assert.hasText(user.getName(),"名称不能为空");
        //JVM 校验
        assert user.getId()<10;
        return user;
    }
}
