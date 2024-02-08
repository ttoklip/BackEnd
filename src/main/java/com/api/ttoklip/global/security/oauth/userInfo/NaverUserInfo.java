package com.api.ttoklip.global.security.oauth.userInfo;

import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProfile() {
        return (String) ((Map) attributes.get("response")).get("profile_image");
    }

    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name"); // "nickname"을 "name"으로 수정
    }

}
