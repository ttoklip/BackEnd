package com.api.ttoklip.global.security.oauth2.userInfo;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProfile() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        try {
            return (String) kakaoProfile.get("profile_image_url");
        } catch (NullPointerException e) {
            throw new ApiException(ErrorType.KAKAO_NOTFOUND_PROFILE_IMAGE_URL);
        }
    }

    @Override
    public String getEmail() {
        try {
            return (String) ((Map) attributes.get("kakao_account")).get("email");
        } catch (NullPointerException e) {
            throw new ApiException(ErrorType.KAKAO_NOTFOUND_EMAIL);
        }
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        try {
            return (String) kakaoProfile.get("nickname");
        } catch (NullPointerException e) {
            throw new ApiException(ErrorType.KAKAO_NOTFOUND_NAME);
        }
    }

}

