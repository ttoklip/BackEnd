package com.api.ttoklip.global.security.oauth.principal;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomOAuth2User implements OAuth2User {

    private final Member member;
    private final Map<String, Object> attributes;

    public static CustomOAuth2User of(final Member member, final Map<String, Object> attributes) {
        return CustomOAuth2User.builder()
                .member(member)
                .attributes(attributes)
                .build();
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // `Member` 엔티티의 `role` 필드를 사용하여 권한을 동적으로 생성합니다.
        return Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getName() {
        if (member.getProvider().equals("kakao")){
            return ((Map<?, ?>) attributes.get("properties")).get("nickname").toString();
        }

        if (member.getProvider().equals("naver")) {
            return ((Map<?, ?>) attributes.get("response")).get("name").toString();
        }

        throw new ApiException(ErrorType.OAUTH_NOTFOUND_NAME);
    }
}
