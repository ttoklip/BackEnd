package com.infrastructure.distribution.manager;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * RedissonLockManager
 *
 * Redis 기반의 분산 락을 제공하는 클래스입니다.
 *
 * 주요 기능:
 * 1. Redis를 통해 락 획득.
 * 2. Redis 락 해제.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RedissonLockManager {

    private final RedissonClient redissonClient;

    public boolean tryLock(final String key, final long waitTime, final long leaseTime) {
        try {
            RLock lock = redissonClient.getLock(key);
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Failed to acquire Redis lock: {}", e.getMessage());
            throw new RuntimeException("Failed to acquire Redis lock", e);
        }
    }

    public void unlock(final String key) {
        try {
            RLock lock = redissonClient.getLock(key);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            log.warn("Failed to release Redis lock: {}", e.getMessage());
        }
    }
}
