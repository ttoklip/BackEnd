package com.infrastructure.lock.distribution.aop;

import com.common.base.Lockable;
import com.common.annotation.DistributedLock;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DistributedLockAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(com.common.annotation.DistributedLock)")
    public Object lockMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        DistributedLock distributedLock = getDistributedLock(joinPoint);

        String keyPrefix = distributedLock.keyPrefix();
        String lockKey = generateLockKey(joinPoint.getArgs(), keyPrefix);

        RLock lock = redissonClient.getLock(lockKey);
        boolean lockAcquired = false;

        try {
            log.info("Trying to acquire lock for key: {}", lockKey);
            lockAcquired = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!lockAcquired) {
                log.warn("Lock is already held for key: {}", lockKey);
                throw new ApiException(ErrorType.DUPLICATED_CREATE_BOARD_REQUEST);
            }

            log.info("Lock acquired successfully for key: {}", lockKey);
            return joinPoint.proceed();
        } catch (InterruptedException | IllegalMonitorStateException e) {
            log.error("Failed to acquire lock due to interruption: {}", e.getMessage(), e);
            throw new ApiException(ErrorType.DUPLICATED_CREATE_BOARD_REQUEST);
        } finally {
            releaseLockIfHeld(lock, lockAcquired);
        }
    }

    private DistributedLock getDistributedLock(final ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(DistributedLock.class);
    }

    private String generateLockKey(Object[] args, String keyPrefix) {
        return Arrays.stream(args)
                .filter(arg -> arg instanceof Lockable)
                .map(arg -> keyPrefix + ((Lockable) arg).getLockKey())
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorType.INVALID_METHOD));
    }

    private void releaseLockIfHeld(RLock lock, boolean lockAcquired) {
        if (lockAcquired && lock.isHeldByCurrentThread()) {
            log.info("Releasing lock for key: {}", lock.getName());
            lock.unlock();
        }
    }
}
