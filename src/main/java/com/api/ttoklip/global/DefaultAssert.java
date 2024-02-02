package com.api.ttoklip.global;

import com.api.ttoklip.domain.user.domain.User;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.DefaultAuthenticationException;
import com.api.ttoklip.global.exception.ErrorType;
import org.springframework.util.Assert;

import java.util.Optional;

public class DefaultAssert extends Assert {
    public static void isTrue(boolean value) {
        if (!value) {
            throw new ApiException(ErrorType.INVALID_CHECK);
        }
    }


    public static void isAuthentication(String message) {
        throw new DefaultAuthenticationException(message);
    }

    public static void isAuthentication(boolean value) {
        if (!value) {
            throw new DefaultAuthenticationException(ErrorType.INVALID_AUTHENTICATION);
        }
    }

    public static void isOptionalPresent(Optional<User> user) {
    }
}
