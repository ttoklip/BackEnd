package com.api.ttoklip.global;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import com.api.ttoklip.domain.aop.filtering.DistributedLockAspect;
import com.api.ttoklip.global.config.RedisTestContainerConfig;
import com.api.ttoklip.global.config.TestSecurityConfig;
import com.api.ttoklip.global.security.auth.controller.AuthController;
import com.api.ttoklip.global.security.auth.service.AuthService;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@EnableAspectJAutoProxy
@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import({DistributedLockAspect.class, RedisTestContainerConfig.class, TestSecurityConfig.class})
public class SignupConcurrencyTest {

    @MockBean
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    @DisplayName("TestContainer Redis connect")
    public void contextLoads() {
        // RedissonClient가 주입되었는지 확인
        assertNotNull(redissonClient);
    }

}
