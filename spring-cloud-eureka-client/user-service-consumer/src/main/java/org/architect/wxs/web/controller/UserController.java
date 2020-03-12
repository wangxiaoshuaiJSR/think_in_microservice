package org.architect.wxs.web.controller;

import org.architect.wxs.domain.User;
import org.architect.wxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/save")
    public User saveUser(@RequestParam String name){
        User user=new User();
        user.setName(name);
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
