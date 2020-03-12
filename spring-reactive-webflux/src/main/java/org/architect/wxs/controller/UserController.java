package org.architect.wxs.controller;

import org.architect.wxs.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/mvc")
    public Boolean save(User user){
        return true;
    }
}
