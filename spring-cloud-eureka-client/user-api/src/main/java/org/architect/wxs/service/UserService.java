package org.architect.wxs.service;

import org.architect.wxs.domain.User;

import java.util.Collection;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：
 */
public interface UserService {

    boolean save(User user);

    Collection<User> findAll();
}
