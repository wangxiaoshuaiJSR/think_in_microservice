package org.architect.wxs.mvc;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CircuitBreakerHandlerInterceptor implements HandlerInterceptor {

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
//        if ("/middle/say".equals(request.getRequestURI()) && ex instanceof TimeoutException) {
//            Writer writer = response.getWriter();
//            writer.write(errorContent(""));
//        }
    }

    public String errorContent(String message) {
        return "Fault";
    }

}
