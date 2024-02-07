package com.api.ttoklip.global.config.company;

import com.api.ttoklip.domain.user.domain.Provider;
import com.api.ttoklip.global.config.OAuth2UserInfo;

import java.util.Map;

public class Naver extends OAuth2UserInfo {
    public Naver(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response != null) {
            return (String) response.get("profile_image"); // 네이버 프로필 이미지 URL
        }
        return null;
    }

    @Override
    public String getProvider() {
        return Provider.naver.toString();
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("name");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("email");
    }

}
