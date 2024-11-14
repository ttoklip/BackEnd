package com.infrastructure.filtering;

import com.common.annotation.FilterBadWord;
import com.infrastructure.util.BadWordUtil;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2) // 분산락 AOP 다음 우선순위로 설정
public class CheckBadWordAspect {

    @Before(value = "@annotation(filterBadWord)")
    public void beforePostCreateBadWordFiltering(JoinPoint joinPoint, FilterBadWord filterBadWord) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof PostRequest request) {
            BadWordUtil.isBadWord(request.getTitle(), request.getContent());
        }

    }
    // 3. Local 닉네임 중복 검사
    // 4. Oauth 닉네임 중복 검사
    @Pointcut("execution(* com.api.profile.presentation.OurServiceJoinController.check*Nickname(..))")
    private void nicknameDuplicationCheckPointCut() {
    }

    @Before("nicknameDuplicationCheckPointCut()")
    public void beforeNicknameDuplicationCheckBadWordFiltering(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args)
                .filter(arg -> arg instanceof String)
                .map(arg -> (String) arg)
                .findFirst()
                .ifPresent(BadWordFilter::isBadWord);
    }

}
