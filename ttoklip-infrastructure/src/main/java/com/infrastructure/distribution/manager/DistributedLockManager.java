package com.infrastructure.distribution.manager;

public interface DistributedLockManager {
    boolean tryLock(String key, long waitTime, long leaseTime);
    void unlock(String key);
}