package com.api.ttoklip.domain.aop.filtering;

import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordCreate;
import com.api.ttoklip.domain.aop.filtering.annotation.CheckBadWordUpdate;
import com.api.ttoklip.domain.common.PostRequest;
import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.global.util.BadWordFilter;
import java.util.Arrays;
import java.util.Objects;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
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
    // 2. Local 닉네임 중복 검사
    // 3. Oauth 닉네임 중복 검사
    @Pointcut("execution(* com.api.ttoklip.domain.privacy.controller.OurServiceJoinController.register(..)) || " +
            "execution(* com.api.ttoklip.domain.privacy.controller.OurServiceJoinController.check*Nickname(..))")
    private void nicknameMethodsPointCut() {
    }

    @Before("nicknameMethodsPointCut()")
    public void beforeNicknameBadWordFiltering(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .map(arg -> {
                    if (arg instanceof PrivacyCreateRequest) {
                        return ((PrivacyCreateRequest) arg).getNickname();
                    }
                    if (arg instanceof String) {
                        return (String) arg;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(BadWordFilter::isBadWord);
    }

}
