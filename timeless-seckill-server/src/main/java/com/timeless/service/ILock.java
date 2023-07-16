package com.timeless.service;

/**
 * @author timeless
 * @create 2023-07-16 10:42
 */
public interface ILock {


    boolean tryLock(long timeout);

    void unlock();

}
