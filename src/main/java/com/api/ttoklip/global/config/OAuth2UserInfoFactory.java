package com.api.ttoklip.global.config;

import com.api.ttoklip.domain.user.domain.Provider;
import com.api.ttoklip.global.DefaultAssert;
import com.api.ttoklip.global.config.company.Kakao;
import com.api.ttoklip.global.config.company.Naver;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(Provider.kakao.toString())) {
            System.out.println("registrationId = " + registrationId);
            Kakao kakao = new Kakao(attributes);
            return kakao;
        } else if (registrationId.equalsIgnoreCase(Provider.naver.toString())) {
            System.out.println("registrationId = " + registrationId);
            Naver naver = new Naver(attributes);
            return naver;
        } else {
            DefaultAssert.isAuthentication("해당 oauth2 기능은 지원하지 않습니다.");
        }
        return null;
    }
}
