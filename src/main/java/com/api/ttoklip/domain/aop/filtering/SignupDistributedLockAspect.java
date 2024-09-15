package com.api.ttoklip.domain.aop.filtering;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.auth.dto.request.AuthRequest;
import com.api.ttoklip.global.security.oauth2.dto.OAuthLoginRequest;
import com.api.ttoklip.global.util.TokenHasher;
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

    @Pointcut("execution(* com.api.ttoklip.global.security.oauth2.controller.OAuthController.login(..))")
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
                        return getLockKey(((AuthRequest) arg).getEmail(), LOCAL_SIGNUP_KEY_PREFIX);
                    }
                    if (arg instanceof OAuthLoginRequest) {
                        // OAuth2UserInfo에서 AccessToken을 가져와 20글자로 줄이고 암호화
                        String encryptedToken = encryptToken(((OAuthLoginRequest) arg).getAccessToken());
                        return getLockKey(encryptedToken, OAUTH_SIGNUP_KEY_PREFIX);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorType.INVALID_METHOD));
    }

    private String getLockKey(String uniqueKey, String prefix) {
        if (uniqueKey == null) {
            throw new ApiException(ErrorType.INVALID_UNIQUE_KEY_TYPE);
        }
        return prefix + uniqueKey;
    }

    private String encryptToken(String token) {
        // SHA-256 해시 적용하여 20글자로 자르기
        String hashedToken = TokenHasher.hashToken(token, 20);
        log.info("encryptToken() hashedToken = {}", hashedToken);
        return hashedToken;
    }


    private void releaseLockIfHeld(RLock lock, boolean lockAcquired) {
        if (lockAcquired && lock.isHeldByCurrentThread()) {
            log.info("flag3. Releasing lock for key: {}", lock.getName());
            lock.unlock();
        }
    }
}
