package com.timeless.service.impl;

import com.timeless.service.ILock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author timeless
 * @create 2023-07-16 10:43
 */
public class ILockImpl implements ILock {

    private final StringRedisTemplate stringRedisTemplate;

    private final String name;

    public ILockImpl(StringRedisTemplate stringRedisTemplate, String name) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.name = name;
    }

    private static final String PREFIX = "lock:";


    @Override
    public boolean tryLock(long timeout) {

        String key = PREFIX + name;
        String value = Thread.currentThread().getId() + "";
        return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS));

    }

    @Override
    public void unlock() {
        stringRedisTemplate.delete(PREFIX + name);
    }
}
