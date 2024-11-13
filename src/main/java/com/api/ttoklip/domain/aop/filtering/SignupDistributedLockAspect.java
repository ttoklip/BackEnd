package com.api.ttoklip.domain.aop.filtering;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.local.dto.request.AuthRequest;
import com.api.ttoklip.global.security.oauth2.userInfo.OAuth2UserInfo;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE) // 욕설 검증 AOP 보다 먼저 동작
public class SignupDistributedLockAspect {

    private static final String LOCAL_SIGNUP_KEY_PREFIX = "local_signup:";
    private static final String OAUTH_SIGNUP_KEY_PREFIX = "oauth_signup:";
    private final RedissonClient redissonClient;

    @Pointcut("execution(* com.api.ttoklip.global.security.auth.controller.AuthController.signup(..))")
    private void localSignupMethodPointcut() {
    }

    @Pointcut("execution(* com.api.ttoklip.global.security.oauth2.service.OAuthRegisterService.registerNewMember(..))")
    private void oauthSignupMethodPointcut() {
    }

    @Around("localSignupMethodPointcut() || oauthSignupMethodPointcut()")
    public Object lockSignupMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String lockKey = generateLockKey(joinPoint.getArgs());

        RLock lock = redissonClient.getLock(lockKey);
        boolean lockAcquired = false;

        try {
            log.info("flag 1. Trying to acquire lock for key: {}", lockKey);
            lockAcquired = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!lockAcquired) {
                log.warn("flag 2-1. Lock is already held for key: {}", lockKey);
                throw new ApiException(ErrorType.DUPLICATED_SIGNUP_REQUEST);
            }

            log.info("flag 2. Lock acquired successfully for key: {}", lockKey);
            return joinPoint.proceed();
        } catch (InterruptedException | IllegalMonitorStateException e) {
            log.error("flag 2-2. Failed to acquire lock due to interruption: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.DUPLICATED_SIGNUP_REQUEST);
        } finally {
            releaseLockIfHeld(lock, lockAcquired);
        }
    }

    private String generateLockKey(Object[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof AuthRequest) {
                        String email = ((AuthRequest) arg).getEmail();
                        return getLockKey(email, LOCAL_SIGNUP_KEY_PREFIX);
                    }
                    if (arg instanceof OAuth2UserInfo) {
                        String email = ((OAuth2UserInfo) arg).getEmail();
                        return getLockKey(email, OAUTH_SIGNUP_KEY_PREFIX);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorType.INVALID_METHOD));
    }

    private String getLockKey(String email, String prefix) {
        if (email == null) {
            throw new ApiException(ErrorType.INVALID_EMAIL_KEY_TYPE);
        }
        return prefix + email;
    }

    private void releaseLockIfHeld(RLock lock, boolean lockAcquired) {
        if (lockAcquired && lock.isHeldByCurrentThread()) {
            log.info("flag3. Releasing lock for key: {}", lock.getName());
            lock.unlock();
        }
    }
}
