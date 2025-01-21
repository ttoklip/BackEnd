package com.infrastructure.distribution.service;

import com.infrastructure.distribution.manager.DistributedLockManager;
import com.infrastructure.distribution.manager.LocalLockManager;
import com.infrastructure.distribution.manager.RedissonLockManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * DistributedLockService
 *
 * 이 서비스는 Redis 분산 락과 Local Lock을 사용하여 잠금을 관리합니다.
 * Redis가 우선적으로 사용되며, 실패 시 Local Lock으로 Fallback됩니다.
 *
 * 주요 기능:
 * 1. Redis를 통한 분산 락 획득.
 * 2. 실패 시 Local Lock으로 Fallback.
 * 3. Write-Through 방식으로 Local Lock도 함께 잠금 처리.
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class DistributedLockService implements DistributedLockManager {

    private final RedissonLockManager redissonLockManager;
    private final LocalLockManager localLockManager;

    @Override
    public boolean tryLock(final String key, final long waitTime, final long leaseTime) {
        boolean redisLockAcquired = false;
        boolean localLockAcquired = false;

        try {
            // 1. Redis 락 시도
            redisLockAcquired = redissonLockManager.tryLock(key, waitTime, leaseTime);
            if (!redisLockAcquired) {
                log.warn("Redis lock failed, falling back to Local lock for key: {}", key);
                localLockAcquired = localLockManager.tryLock(key, waitTime, leaseTime);
            }

            // 2. Write-Through 적용
            if (redisLockAcquired) {
                log.info("Redis lock acquired, synchronizing to Local lock...");
                // Local에도 동일하게 잠금 설정
                localLockManager.tryLock(key, 0, leaseTime);
            }

            return redisLockAcquired || localLockAcquired;

        } catch (Exception e) {
            log.error("Failed to acquire lock for key: {}, falling back to Local lock", key, e);
            return localLockManager.tryLock(key, waitTime, leaseTime);
        }
    }

    @Override
    public void unlock(final String key) {
        try {
            redissonLockManager.unlock(key);
            log.info("Redis lock released for key: {}", key);
        } catch (Exception e) {
            log.warn("Failed to release Redis lock, attempting to release Local lock for key: {}", key, e);
        } finally {
            // 항상 Local 락 해제 시도
            localLockManager.unlock(key);
            log.info("Local lock released for key: {}", key);
        }
    }
}
