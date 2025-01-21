package com.infrastructure.cache.repository;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

/**
 * LocalCacheRepository
 *
 * JVM 메모리 내에서 작동하는 로컬 캐시 저장소입니다.
 * Redis 장애 시 Fallback으로 사용됩니다.
 *
 * 주요 기능:
 * 1. 메모리에 데이터를 저장 및 조회.
 * 2. 만료 시간이 지난 데이터를 필터링.
 * 3. 모든 활성 키 반환.
 */

@Repository
public class LocalCacheRepository {

    private final Map<String, CacheEntry> localCache = new ConcurrentHashMap<>();

    public String get(final String key) {
        CacheEntry entry = localCache.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.value();
        }
        return null;
    }

    public void set(final String key, final String value, final long duration) {
        localCache.put(
                key, new CacheEntry(
                        value, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(duration)
                )
        );
    }

    public boolean exists(final String key) {
        CacheEntry entry = localCache.get(key);
        return entry != null && !entry.isExpired();
    }

    public void delete(final String key) {
        localCache.remove(key);
    }

    /**
     * 모든 키 반환
     */
    public Set<String> getAllKeys() {
        return localCache.entrySet().stream()
                .filter(entry -> !entry.getValue().isExpired())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private record CacheEntry(String value, long expireTime) {
        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
}
