package org.architect.wxs.service;

import org.architect.wxs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：
 */
@Service
public class UserServiceProxy implements UserService {
    private static final String PROVIDER_SERVER_URL_PREFIX = "http://user-service-provider/";

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public boolean save(User user) {
        User result= restTemplate.postForObject(PROVIDER_SERVER_URL_PREFIX + "/user/save",user, User.class);
        return result!=null;
    }

    @Override
    public Collection<User> findAll() {
        return restTemplate.getForObject(PROVIDER_SERVER_URL_PREFIX + "/list",Collection.class);
    }
}
