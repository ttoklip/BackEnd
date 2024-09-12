package com.api.ttoklip.domain.aop.filtering;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.auth.dto.request.AuthRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {

    private static final String LOCAL_SIGNUP_KEY_PREFIX = "local_signup:";
    private final RedissonClient redissonClient;

    @Around("execution(* com.api.ttoklip.global.security.auth.controller.AuthController.signup(..))")
    public Object lockSignupMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String lockKey = generateLockKey(joinPoint.getArgs());

        RLock lock = redissonClient.getLock(lockKey);
        boolean lockAcquired = false;

        try {
            log.info("flag 1. Trying to acquire lock for key: {}", lockKey);
            lockAcquired = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!lockAcquired) {
                log.warn("flag 2-1. Lock is already held for key: {}", lockKey);
                throw new ApiException(ErrorType.DUPLICATED_LOCAL_SIGNUP_REQUEST);
            }

            log.info("flag 2. Lock acquired successfully for key: {}", lockKey);
            return joinPoint.proceed();
        } catch (InterruptedException | IllegalMonitorStateException e) {
            log.error("flag 2-2. Failed to acquire lock due to interruption: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.DUPLICATED_LOCAL_SIGNUP_REQUEST);
        } finally {
            releaseLockIfHeld(lock, lockAcquired);
        }
    }

    private String generateLockKey(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg instanceof AuthRequest)
                .map(arg -> ((AuthRequest) arg).getEmail())
                .findFirst()
                .map(email -> LOCAL_SIGNUP_KEY_PREFIX + email)
                .orElseThrow(() -> new ApiException(ErrorType.INVALID_MAIL_TYPE));
    }

    private void releaseLockIfHeld(RLock lock, boolean lockAcquired) {
        if (lockAcquired && lock.isHeldByCurrentThread()) {
            log.info("flag3. Releasing lock for key: {}", lock.getName());
            lock.unlock();
        }
    }
}
