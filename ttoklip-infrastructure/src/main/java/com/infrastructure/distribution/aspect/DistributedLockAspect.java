package com.infrastructure.distribution.aspect;

import com.common.annotation.DistributedLock;
import com.common.base.Lockable;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.infrastructure.distribution.repository.DistributedLockManager;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DistributedLockAspect {

    private final DistributedLockManager distributedLockManager;

    @Around("@annotation(com.common.annotation.DistributedLock)")
    public Object lockMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        DistributedLock distributedLock = getDistributedLock(joinPoint);

        String keyPrefix = distributedLock.keyPrefix();
        String lockKey = generateLockKey(joinPoint.getArgs(), keyPrefix);

        boolean lockAcquired = false;

        try {
            log.info("Trying to acquire lock for key: {}", lockKey);
            lockAcquired = distributedLockManager.tryLock(lockKey, 0, 3);

            if (!lockAcquired) {
                log.warn("Lock is already held for key: {}", lockKey);
                throw new ApiException(ErrorType.DUPLICATED_CREATE_BOARD_REQUEST);
            }

            log.info("Lock acquired successfully for key: {}", lockKey);
            return joinPoint.proceed();
        } finally {
            releaseLockIfHeld(lockKey, lockAcquired);
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

    private void releaseLockIfHeld(String lockKey, boolean lockAcquired) {
        if (lockAcquired) {
            try {
                distributedLockManager.unlock(lockKey);
                log.info("Lock released for key: {}", lockKey);
            } catch (Exception e) {
                log.warn("Failed to release lock for key: {}", lockKey, e);
            }
        }
    }
}
