package com.api.ttoklip.domain.aop.filtering;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.Arrays;
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
@Order(Ordered.HIGHEST_PRECEDENCE) // 욕설 검증 AOP 보다 먼저 동작
@RequiredArgsConstructor
public class BoardDistributedLockAspect {

    private static final String POST_LOCK_KEY_PREFIX = "Post:";
    private final RedissonClient redissonClient;

    @Pointcut("execution(* com.api.ttoklip.domain.question.controller.QuestionPostController.register(..))")
    private void questionRegisterMethodPointcut() {
    }

    @Pointcut("execution(* com.api.ttoklip.domain.honeytip.controller.HoneyTipPostController.register(..))")
    private void honeyTipRegisterMethodPointcut() {
    }

    @Pointcut("execution(* com.api.ttoklip.domain.town.community.controller.CommunityPostController.register(..))")
    private void communityRegisterMethodPointcut() {
    }

    @Pointcut("execution(* com.api.cart.presentation.CartPostController.register(..))")
    private void cartRegisterMethodPointcut() {
    }

    @Around("questionRegisterMethodPointcut() || honeyTipRegisterMethodPointcut() || communityRegisterMethodPointcut() || cartRegisterMethodPointcut()")
    public Object lockRegisterMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String lockKey = generateLockKey(joinPoint.getArgs());

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

    private String generateLockKey(Object[] args) {
        Member currentMember = SecurityUtil.getCurrentMember();
        String email = currentMember.getEmail();

        return Arrays.stream(args)
                .filter(arg -> arg instanceof PostRequest)
                .map(arg -> {
                    PostRequest request = (PostRequest) arg;
                    String title = request.getTitle();
                    return getLockKey(title, email);
                })
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorType.INVALID_METHOD));
    }

    private String getLockKey(String title, String email) {
        if (title == null || email == null) {
            throw new ApiException(ErrorType.INVALID_MAIL_TYPE);
        }
        return POST_LOCK_KEY_PREFIX + title + ":" + email;
    }

    private void releaseLockIfHeld(RLock lock, boolean lockAcquired) {
        if (lockAcquired && lock.isHeldByCurrentThread()) {
            log.info("Releasing lock for key: {}", lock.getName());
            lock.unlock();
        }
    }
}
