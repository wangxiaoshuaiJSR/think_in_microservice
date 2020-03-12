package org.architect.wxs.interceptor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class RestControllerAdvicer {
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object pageNotFound(Throwable throwable){
        return throwable.getMessage();
    }
}
