package com.api.ttoklip.global.security.oauth.userInfo;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuthUserInfoFactory {
    public static OAuth2UserInfo getOAuthAttributes(String provider, Map<String, Object> attributes) {

        if (provider.equals("kakao")) {
            log.info("------------------ 카카오 로그인 요청");
            return new KakaoUserInfo(attributes);
        }

        if (provider.equals("naver")) {
            log.info("------------------ 네이버 로그인 요청");
            return new NaverUserInfo(attributes);
        }

        log.error("provider가 올바르지 않습니다.");
        throw new ApiException(ErrorType.OAUTH_INVALID_PROVIDER);
    }
}
