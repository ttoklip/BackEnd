package com.infrastructure.cache.repository;

public interface CacheRepository {
    String get(String key);
    void set(String key, String value, long duration);
    boolean exists(String key);
    void delete(String key);
}