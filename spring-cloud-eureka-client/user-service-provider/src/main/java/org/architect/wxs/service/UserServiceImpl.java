package org.architect.wxs.service;

import org.architect.wxs.domain.User;
import org.architect.wxs.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link}
 *
 * @author wangxiaoshuai on 2020/3/1
 * @javadoc ：真正的服务提供者
 */
@Service
public class UserServiceImpl implements UserService {
    private ConcurrentMap<Long,User>  userConcurrentMap = new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator = new AtomicLong(0);
    @Override
    public boolean save(User user) {
        long incrementAndGet = idGenerator.incrementAndGet();
        user.setId(incrementAndGet);
        return userConcurrentMap.putIfAbsent(incrementAndGet,user)==null;
    }

    @Override
    public Collection<User> findAll() {
        return userConcurrentMap.values();
    }
}
