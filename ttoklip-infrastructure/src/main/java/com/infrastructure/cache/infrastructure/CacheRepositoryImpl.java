package com.infrastructure.cache.infrastructure;

import com.infrastructure.cache.CacheException;
import com.infrastructure.cache.repository.CacheRepository;
import com.infrastructure.cache.repository.LocalCacheRepository;
import com.infrastructure.cache.repository.RedisCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * CacheRepositoryImpl
 *
 * Redis와 Local Cache를 통합 관리하는 클래스입니다.
 * Redis를 우선 사용하며, 실패 시 Local Cache를 사용합니다.
 *
 * 주요 기능:
 * 1. Redis를 우선 사용하고, 실패 시 Local Cache Fallback.
 * 2. Write-Through 방식으로 동시 저장.
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class CacheRepositoryImpl implements CacheRepository {

    private final RedisCacheRepository redisCacheRepository;
    private final LocalCacheRepository localCacheRepository;

    @Override
    public String get(final String key) {
        try {
            String value = redisCacheRepository.get(key);
            if (value != null) {
                return value;
            }
            // Redis에 데이터가 없으면 LocalCache 확인
            return localCacheRepository.get(key);
        } catch (CacheException e) {
            log.warn("Redis failed, falling back to LocalCache");
            return localCacheRepository.get(key);
        }
    }

    @Override
    public void set(final String key, final String value, final long duration) {
        try {
            // Write-Through 전략. Redis, LocalCache 둘 다 저장
            redisCacheRepository.set(key, value, duration);
            localCacheRepository.set(key, value, duration);
        } catch (CacheException e) {
            log.warn("Redis failed, writing only to LocalCache");
            localCacheRepository.set(key, value, duration);
        }
    }

    @Override
    public boolean exists(final String key) {
        try {
            return redisCacheRepository.exists(key) || localCacheRepository.exists(key);
        } catch (CacheException e) {
            return localCacheRepository.exists(key);
        }
    }

    @Override
    public void delete(final String key) {
        try {
            redisCacheRepository.delete(key);
        } catch (CacheException e) {
            log.warn("Redis failed, deleting from LocalCache");
        }
        localCacheRepository.delete(key);
    }
}
