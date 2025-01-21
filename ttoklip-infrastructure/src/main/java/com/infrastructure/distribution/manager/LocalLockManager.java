package com.infrastructure.distribution.manager;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * LocalLockManager
 *
 * 로컬 JVM 메모리 내에서 작동하는 분산 락을 제공합니다.
 *
 * 주요 기능:
 * 1. Local Lock 획득.
 * 2. Local Lock 해제.
 * 3. 모든 활성화된 Lock 키 반환.
 */

@Slf4j
@Component
public class LocalLockManager {

    private final ConcurrentHashMap<String, Lock> localLocks = new ConcurrentHashMap<>();

    public boolean tryLock(final String key, final long waitTime, final long leaseTime) {
        Lock lock = localLocks.computeIfAbsent(key, k -> new ReentrantLock());
        try {
            return lock.tryLock(waitTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.warn("Failed to acquire Local lock: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlock(final String key) {
        Lock lock = localLocks.get(key);
        if (lock != null && ((ReentrantLock) lock).isHeldByCurrentThread()) {
            lock.unlock();
            log.info("Local lock released for key: {}", key);
        }
    }

    public Set<String> getAllActiveKeys() {
        return localLocks.entrySet().stream()
                .filter(entry -> ((ReentrantLock) entry.getValue()).isLocked())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
