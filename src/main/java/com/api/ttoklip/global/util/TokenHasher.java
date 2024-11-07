package com.api.ttoklip.global.util;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class TokenHasher {

    // SHA-256 해시를 생성하는 메서드
    public static String hashToken(String token, final int length) {
        if (length <= 0) {
            throw new ApiException(ErrorType.INVALID_HASH_LENGTH_TYPE);
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(encodedHash).substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing the token", e);
        }
    }
}
