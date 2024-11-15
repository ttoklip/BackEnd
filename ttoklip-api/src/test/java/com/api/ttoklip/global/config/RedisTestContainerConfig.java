//package com.api.ttoklip.global.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Configuration
//@Testcontainers
//public class RedisTestContainerConfig {
//
//    private static final String REDIS_IMAGE = "redis:7.0.8-alpine";
//    private static final int REDIS_PORT = 6379;
//    private static final GenericContainer REDIS_CONTAINER;
//
//    static {
//        REDIS_CONTAINER = new GenericContainer(REDIS_IMAGE)
//                .withExposedPorts(REDIS_PORT)
//                .withReuse(true);
//
//        REDIS_CONTAINER.start();
//    }
//
//    @DynamicPropertySource
//    public static void registerRedisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
//        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(REDIS_PORT)
//                .toString());
//
//        System.out.println("이유 " + REDIS_CONTAINER.getHost());
//    }
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://" + REDIS_CONTAINER.getHost() + ":" + REDIS_CONTAINER.getMappedPort(6379))
//                // DNS 모니터링 비활성화 (테스트에서만 필요)
//                .setDnsMonitoringInterval(-1);
//        return Redisson.create(config);
//    }
//
//}
