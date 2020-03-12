package org.architect.wxs.provider.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.architect.wxs.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
@RestController
public class PersonServiceProviderController {
    private final Random random = new Random();

    private Map<Long,Person> personMap = new ConcurrentHashMap<Long,Person>();
    @PostMapping("/save/person")
    public boolean savePerson(@RequestBody Person person){//方法名可以不统一，但是资源定位符的路径必须统一
        return personMap.put(person.getId(),person)==null;
    }



    @GetMapping("/findAll")
    @HystrixCommand(fallbackMethod = "fallbackForFindAllPersons",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "100")
            }
    )
    public Collection<Person> findAllPerson(){
        try {
            Thread.sleep(random.nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return personMap.values();
    }

    public Collection<Person> fallbackForFindAllPersons(){
        return Collections.EMPTY_LIST;
    }

}
