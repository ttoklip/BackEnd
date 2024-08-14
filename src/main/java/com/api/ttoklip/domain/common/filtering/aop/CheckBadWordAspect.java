package com.api.ttoklip.domain.common.filtering.aop;

import com.api.ttoklip.domain.common.PostCreateRequest;
import com.api.ttoklip.domain.common.filtering.aop.annotation.CheckBadWord;
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
    public void beforePostCreateBadWordFiltering(JoinPoint joinPoint, CheckBadWord checkBadWord) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof PostCreateRequest request) {
            // 제목과 내용을 가져와서 욕설 필터링을 적용
            BadWordFilter.isBadWord(request.getTitle(), request.getContent());
        }

    }
}
