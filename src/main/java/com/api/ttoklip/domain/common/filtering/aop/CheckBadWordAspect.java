package com.api.ttoklip.domain.common.filtering.aop;

import com.api.ttoklip.domain.common.PostRequest;
import com.api.ttoklip.domain.common.filtering.aop.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.common.filtering.aop.annotation.CheckBadWordUpdate;
import com.api.ttoklip.global.util.BadWordFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CheckBadWordAspect {

    @Before(value = "@annotation(checkBadWord)")
    public void beforePostCreateBadWordFiltering(JoinPoint joinPoint, CheckBadWordCreate checkBadWord) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof PostRequest request) {
            BadWordFilter.isBadWord(request.getTitle(), request.getContent());
        }

    }

    @Before(value = "@annotation(checkBadWord)")
    public void beforePostUpdateBadWordFiltering(JoinPoint joinPoint, CheckBadWordUpdate checkBadWord) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 1 && args[1] instanceof PostRequest request) {
            BadWordFilter.isBadWord(request.getTitle(), request.getContent());
        }

    }
}
