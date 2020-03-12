package org.architect.wxs.web.controller;

import org.architect.wxs.domain.User;
import org.architect.wxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：用户服务提供方rest api controller
 */
@RestController
public class UserServiceProviderRestApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public User saveUser(@RequestBody User user){
        if(userService.save(user)){
            return user;
        }
        return null;
    }

    @GetMapping("/list")
    public Collection<User> list(){
        return userService.findAll();
    }
}
