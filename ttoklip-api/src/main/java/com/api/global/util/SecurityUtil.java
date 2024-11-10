package com.api.global.util;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.domain.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException(ErrorType._UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Member) {
            return (Member) principal;
        }

        throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
    }
}
