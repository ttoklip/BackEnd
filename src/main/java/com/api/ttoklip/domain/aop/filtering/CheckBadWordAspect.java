package com.api.ttoklip.domain.aop.filtering;

import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordUpdate;
import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.global.security.local.dto.request.AuthRequest;
import com.api.ttoklip.global.util.BadWordFilter;
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

    @Pointcut("execution(* com.api.ttoklip.domain.common.comment.service.CommentService.register(com.api.ttoklip.domain.common.comment.Comment))")
    private void commentPointCut() {
    }

    @Before(value = "commentPointCut() && args(comment)")
    public void beforeCreateCommentBadWordFiltering(Comment comment) {
        BadWordFilter.isBadWord(comment.getContent());
    }

    // 1. Local 회원가입
    @Pointcut("execution(* com.api.ttoklip.global.security.auth.controller.AuthController.signup(..))")
    private void registerLocalMethodPointCut() {
    }

    @Before("registerLocalMethodPointCut()")
    public void beforeLocalRegisterBadWordFiltering(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args)
                .filter(arg -> arg instanceof AuthRequest)
                .map(arg -> ((AuthRequest) arg).getNickname())
                .findFirst()
                .ifPresent(BadWordFilter::isBadWord);
    }

    // 2. Oauth 회원가입
    @Pointcut("execution(* com.api.profile.presentation.OurServiceJoinController.register(..))")
    private void registerOauthMethodPointCut() {
    }

    @Before("registerOauthMethodPointCut()")
    public void beforeOauthRegisterBadWordFiltering(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args)
                .filter(arg -> arg instanceof PrivacyCreateRequest)
                .map(arg -> ((PrivacyCreateRequest) arg).getNickname())
                .findFirst()
                .ifPresent(BadWordFilter::isBadWord);
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
