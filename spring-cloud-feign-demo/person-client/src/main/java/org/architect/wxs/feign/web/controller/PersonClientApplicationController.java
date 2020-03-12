package org.architect.wxs.feign.web.controller;

import org.architect.wxs.domain.Person;
import org.architect.wxs.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
@RestController
public class PersonClientApplicationController implements PersonService { //请求路径可以继承过来
    private final PersonService personService;
    @Autowired
    public PersonClientApplicationController(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean save(@RequestBody Person person) {//@RequestBody 不具有继承性
        return personService.save(person);
    }

    @Override
    public Collection<Person> findAll() {
        return personService.findAll();
    }
}
