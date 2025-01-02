package com.infrastructure.cache.repository;

import com.infrastructure.cache.CacheException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

/**
 * RedisCacheRepository
 *
 * Redis 기반의 캐시 저장소입니다.
 * Redis를 사용하여 데이터를 저장, 조회, 삭제합니다.
 *
 * 주요 기능:
 * 1. Redis를 통해 데이터를 저장 및 조회.
 * 2. Redis에 데이터가 없는 경우 예외 처리.
 */

@Repository
@RequiredArgsConstructor
public class RedisCacheRepository {

    private final StringRedisTemplate redisTemplate;

    @Value("${spring.cache.redis.timeout}")
    private long redisTimeout;

    public String get(final String key) {
        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            return ops.get(key);
        } catch (DataAccessException e) {
            throw new CacheException("Redis connection failed", e);
        }
    }

    public void set(
            final String key,
            final String value,
            final long duration
    ) {
        try {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(key, value, Duration.ofSeconds(duration));
        } catch (DataAccessException e) {
            throw new CacheException("Redis connection failed", e);
        }
    }

    public boolean exists(final String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (DataAccessException e) {
            throw new CacheException("Redis connection failed", e);
        }
    }

    public void delete(final String key) {
        try {
            redisTemplate.delete(key);
        } catch (DataAccessException e) {
            throw new CacheException("Redis connection failed", e);
        }
    }
}
