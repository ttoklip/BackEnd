package com.api.ttoklip.global.security.auth.userInfo;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaverUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    @Override
    public String getProfile() {
        try {
            return (String) ((Map) attributes.get("response")).get("profile_image");
        } catch (NullPointerException e) {
            throw new ApiException(ErrorType.NAVER_NOTFOUND_EMAIL);
        }
    }

    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name"); // "nickname"을 "name"으로 수정
    }

}
