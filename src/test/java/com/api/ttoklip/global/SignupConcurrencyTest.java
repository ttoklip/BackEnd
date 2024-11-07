//package com.api.ttoklip.global;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.api.ttoklip.domain.aop.filtering.BoardDistributedLockAspect;
//import com.api.ttoklip.global.config.RedisTestContainerConfig;
//import com.api.ttoklip.global.config.TestSecurityConfig;
//import com.api.ttoklip.global.security.auth.controller.AuthController;
//import com.api.ttoklip.global.security.auth.service.AuthService;
//import com.api.ttoklip.global.security.jwt.JwtProvider;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.redisson.api.RBucket;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//
//@EnableAspectJAutoProxy
//@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Import({BoardDistributedLockAspect.class, RedisTestContainerConfig.class, TestSecurityConfig.class})
//public class SignupConcurrencyTest {
//

/**
 * ToDo 멀티 프로세스 환경에서의 분산락 자동 테스트 고민해보기
 */


//    @MockBean
//    AuthenticationFailureHandler authenticationFailureHandler;
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private AuthService authService;
//    @MockBean
//    private JwtProvider jwtProvider;
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Test
//    @DisplayName("Test Redis connection by setting and getting a value")
//    public void testRedisConnection() {
//        // Given: Redis에 저장할 키와 값을 정의
//        String testKey = "testKey";
//        String testValue = "testValue";
//
//        // When: RedissonClient를 사용하여 Redis에 값을 설정
//        RBucket<String> bucket = redissonClient.getBucket(testKey);
//        bucket.set(testValue);
//        System.out.println("Redis에 값 설정 완료: " + testKey + " = " + testValue);
//
//        // Then: Redis에서 값을 가져와 설정한 값과 동일한지 확인
//        String fetchedValue = bucket.get();
//        System.out.println("Redis에서 가져온 값: " + fetchedValue);
//        assertEquals(testValue, fetchedValue, "Redis should return the value that was set");
//    }
//
//}
