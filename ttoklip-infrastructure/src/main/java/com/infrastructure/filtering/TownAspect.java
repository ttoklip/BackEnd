package com.infrastructure.filtering;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TownAspect {

    private static final String SEOUL = "서울특별시";

    /**
     * 2024.10.01 서울특별시 한정 -> 전국으로 확대
     */

//    @Pointcut("execution(* com.api.ttoklip.domain.town..*Controller.*(..))")
//    private void townPointcut() {
//    }
//
//    @Before("townPointcut()")
    public void filterWriterStreetSeoul() {
        Member currentMember = getCurrentMember();
        if (!currentMember.getStreet().startsWith(SEOUL)) {
            throw new ApiException(ErrorType.INVALID_STREET_TYPE);
        }
    }
}
