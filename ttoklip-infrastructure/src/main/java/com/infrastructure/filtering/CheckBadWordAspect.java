package com.infrastructure.filtering;

import com.common.base.Filterable;
import com.common.annotation.FilterBadWord;
import com.infrastructure.util.BadWordUtil;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2) // 분산락 AOP 다음 우선순위로 설정
public class CheckBadWordAspect {

    @Before(value = "@annotation(filterBadWord)")
    public void beforeBadWordFiltering(JoinPoint joinPoint, FilterBadWord filterBadWord) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args)
                .filter(arg -> arg instanceof Filterable)
                .map(arg -> ((Filterable) arg).getFilterContent())
                .forEach(BadWordUtil::isBadWord);
    }
}