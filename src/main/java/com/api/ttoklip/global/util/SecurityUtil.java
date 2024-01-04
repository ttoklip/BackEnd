package com.api.ttoklip.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    // ToDo 현재 로그인한 사용자의 정보를 가져오는 메서드이며, oauth 작업이 완료된 후에 사용할 수 있습니다.
    /*
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

     */
}
