package com.api.ttoklip.global.config.company;

import com.api.ttoklip.domain.user.domain.Provider;
import com.api.ttoklip.global.config.OAuth2UserInfo;

import java.util.Map;

public class Kakao extends OAuth2UserInfo {
    public Kakao(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getProvider() {
        return Provider.kakao.toString();
    } // naver, kakao

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null) {
                return (String) profile.get("profile_image_url"); // 카카오 프로필 이미지 URL
            }
        }
        return null;
    }


    @Override
    public String getName() {
        // 프로필 정보는 kakao_account 내의 profile 객체 안에 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null) {
                return (String) profile.get("nickname");
            }
        }
        return null;
    }

    @Override
    public String getEmail() {
        // 이메일 정보는 kakao_account 객체 안에 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            return (String) kakaoAccount.get("email");
        }
        return null;
    }

}
