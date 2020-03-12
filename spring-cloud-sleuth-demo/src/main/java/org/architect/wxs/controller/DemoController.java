package org.architect.wxs.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/7
 * @javadoc ：[-,1ccef72d4965a499,1ccef72d4965a499,false]
 */
@RestController
public class DemoController {
    protected final Logger logger= LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    public DemoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/index")
    public String index(){
        String returnValue = "hello,world";
        logger.info("{} index() : {}",getClass().getSimpleName(),returnValue);
        return "hello,world";
    }

    @GetMapping("/to/zuul/person-client/findAll")
    public Object get(){
        String url = "http://spring-cloud-zuul/person-client/findAll";
        return restTemplate.getForObject(url,Object.class);
    }
}
