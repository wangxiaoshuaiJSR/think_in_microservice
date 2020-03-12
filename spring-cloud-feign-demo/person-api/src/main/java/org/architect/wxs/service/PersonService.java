package org.architect.wxs.service;

import org.architect.wxs.domain.Person;
import org.architect.wxs.hystrix.PersonServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
@FeignClient(value = "person-service",fallback = PersonServiceFallback.class)//服务提供方的应用名称
public interface PersonService {

    @PostMapping("/save/person")
    public boolean save(@RequestBody Person person);
    @GetMapping("/findAll")
    public Collection<Person> findAll();
}
