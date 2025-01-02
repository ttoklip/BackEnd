package com.infrastructure.distribution.service;

import com.infrastructure.distribution.repository.LocalLockManager;
import com.infrastructure.distribution.repository.RedissonLockManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * LockSynchronizationService
 *
 * 이 서비스는 Redis 락이 일시적으로 다운된 상황에서 Local Lock이 사용된 경우,
 * Local Lock 데이터를 Redis로 동기화하는 역할을 합니다.
 *
 * - 주기적으로 Local Lock을 Redis로 동기화합니다.
 * - 비동기적으로 각 키를 처리하여 성능 병목을 방지합니다.
 *
 * 주요 기능:
 * 1. 5분마다 Local Lock 데이터를 Redis로 동기화합니다.
 * 2. 개별 Lock 키를 비동기적으로 Redis로 이전합니다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LockSynchronizationService {

    private final RedissonLockManager redissonLockManager;
    private final LocalLockManager localLockManager;

    /**
     * 5분마다 Local Lock을 Redis로 동기화
     */
    @Scheduled(fixedRate = 300000)
    public void syncLocalLocksToRedis() {
        log.info("Starting batch synchronization of Local Locks to Redis...");
        Set<String> allKeys = localLockManager.getAllActiveKeys();
        List<String> keyList = new ArrayList<>(allKeys);

        // 한 번에 처리할 배치 크기
        int batchSize = 10;

        for (int i = 0; i < keyList.size(); i += batchSize) {
            List<String> batch = keyList.subList(i, Math.min(i + batchSize, keyList.size()));
            batch.forEach(this::syncLockAsync);
        }
    }

    /**
     * 비동기적으로 개별 Lock을 Redis에 동기화
     */
    @Async
    public void syncLockAsync(final String key) {
        try {
            if (redissonLockManager.tryLock(key, 0, 60)) {
                localLockManager.unlock(key);
                log.info("Lock key {} synchronized to Redis.", key);
            } else {
                log.warn("Failed to acquire Redis lock for key: {}", key);
            }
        } catch (Exception e) {
            log.warn("Exception during lock synchronization for key {}: {}", key, e.getMessage());
        }
    }
}
