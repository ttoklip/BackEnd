package com.infrastructure.cache.service;

import com.infrastructure.cache.repository.LocalCacheRepository;
import com.infrastructure.cache.repository.RedisCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * CacheSynchronizationService
 *
 * 이 서비스는 Redis Image가 일시적으로 다운되어 Local Cache가 사용된 경우,
 * LocalCache 데이터를 주기적으로 Redis로 동기화하는 역할을 합니다.
 *
 * 주요 기능:
 * 1. 5분마다 LocalCache 데이터를 Redis로 동기화
 * 2. 각 키를 비동기적으로 처리하여 성능 병목을 방지
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheSynchronizationService {

    private final RedisCacheRepository redisCacheRepository;
    private final LocalCacheRepository localCacheRepository;

    /**
     * 5분마다 LocalCache 데이터를 Redis로 동기화
     */
    @Scheduled(fixedRate = 300000) // 5분마다 실행
    public void syncLocalCacheToRedis() {
        log.info("Starting batch synchronization of LocalCache to Redis...");
        Set<String> allKeys = localCacheRepository.getAllKeys();
        List<String> keyList = new ArrayList<>(allKeys);
        int batchSize = 10; // 한 번에 처리할 배치 크기

        for (int i = 0; i < keyList.size(); i += batchSize) {
            List<String> batch = keyList.subList(i, Math.min(i + batchSize, keyList.size()));
            batch.forEach(this::syncKeyAsync);
        }
    }

    /**
     * 개별 키를 Redis로 비동기 동기화
     */
    @Async
    public void syncKeyAsync(String key) {
        try {
            String value = localCacheRepository.get(key);
            if (value != null) {
                redisCacheRepository.set(key, value, 60); // TTL 60초
                localCacheRepository.delete(key); // 동기화 후 LocalCache에서 제거
                log.info("Key '{}' synchronized to Redis.", key);
            }
        } catch (Exception e) {
            log.warn("Failed to synchronize key '{}' to Redis: {}", key, e.getMessage());
        }
    }
}
