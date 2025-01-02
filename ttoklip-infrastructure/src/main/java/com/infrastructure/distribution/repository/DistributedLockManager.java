package com.infrastructure.distribution.repository;

public interface DistributedLockManager {
    boolean tryLock(String key, long waitTime, long leaseTime);
    void unlock(String key);
}