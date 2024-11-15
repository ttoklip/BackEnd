package com.infrastructure.filtering;

import com.common.base.Filterable;
import com.common.annotation.FilterBadWord;
import com.infrastructure.util.BadWordUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2) // 분산락, 로깅 후에 동작
public class CheckBadWordAspect {

    @Before("@annotation(com.common.annotation.FilterBadWord)")
    public void beforeBadWordFiltering(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        Arrays.stream(args)
                .filter(arg -> arg instanceof Filterable)
                .map(arg -> ((Filterable) arg).getFilterContent())
                .forEach(BadWordUtil::isBadWord);
    }
}