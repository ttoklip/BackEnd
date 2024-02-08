package com.api.ttoklip.global.security.oauth.userInfo;

import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    @Override
    public String getProfile() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return (String) kakaoProfile.get("profile_image_url");
    }

    // 카카오에서 이메일 미제공,,,
//    @Override
//    public String getEmail() {
//        return (String) ((Map) attributes.get("kakao_account")).get("email");
//    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return (String) kakaoProfile.get("nickname");
    }

    public Long getKakaoId() {
        return (Long) attributes.get("id");
    }

}

