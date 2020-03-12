package org.architect.wxs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestDemoController {
    @GetMapping("/index")
    public String index(){
        return "hello,world";
    }

    @GetMapping("/npe")
    public String npe(){
        throw  new NullPointerException("这是故意抛出的异常");
    }

    @GetMapping("/404.html")
    public Object pageNotFound(HttpStatus status, HttpServletRequest httpServletRequest, Throwable throwable){
        Map<String,Object> error = new HashMap<>();

        error.put("statusCode",httpServletRequest.getAttribute("javax.servlet.error.status.code"));
        error.put("requestUri",httpServletRequest.getAttribute("javax.servlet.error.status.request_uri"));
        return error;
    }
}
