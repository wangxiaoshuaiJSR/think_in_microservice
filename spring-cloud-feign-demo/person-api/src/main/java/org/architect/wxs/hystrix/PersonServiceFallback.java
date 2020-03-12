package org.architect.wxs.hystrix;

import org.architect.wxs.domain.Person;
import org.architect.wxs.service.PersonService;

import java.util.Collection;
import java.util.Collections;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/3
 * @javadoc ：
 */
public class PersonServiceFallback implements PersonService {
    @Override
    public boolean save(Person person) {
        return false;
    }

    @Override
    public Collection<Person> findAll() {
        return Collections.EMPTY_LIST;
    }
}
