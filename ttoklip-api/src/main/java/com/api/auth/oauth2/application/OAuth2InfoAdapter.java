package com.api.auth.oauth2.application;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.domain.userInfo.KakaoUserInfo;
import com.domain.member.domain.userInfo.NaverUserInfo;
import com.domain.member.domain.userInfo.OAuth2UserInfo;
import com.domain.member.domain.vo.Provider;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2InfoAdapter {

    private static final String KAKAO_API_PREFIX = "https://kapi.kakao.com";
    private static final String NAVER_API_PREFIX = "https://openapi.naver.com";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final String USER_INFORMATION_SUFFIX = "/v2/user/me";
    private static final String NAVER_INFORMATION_SUFFIX = "/v1/nid/me";

    public OAuth2UserInfo getUserInfo(String provider, String accessToken) {
        if (provider.equals(Provider.KAKAO.getType())) {
            return getKakaoUserInfo(accessToken);
        }

        if (provider.equals(Provider.NAVER.getType())) {
            return getNaverUserInfo(accessToken);
        }

        throw new ApiException(ErrorType.OAUTH_INVALID_PROVIDER);
    }

    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(KAKAO_API_PREFIX)
                .defaultHeader(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + accessToken)
                .build();

        Map<String, Object> attributes = webClient.get()
                .uri(USER_INFORMATION_SUFFIX)
                .retrieve()
                .onStatus(UNAUTHORIZED::equals, response -> Mono.error(new ApiException(ErrorType.KAKAO_TOKEN_INVALID)))
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        return new KakaoUserInfo(attributes);
    }

    private OAuth2UserInfo getNaverUserInfo(final String accessToken) {

        WebClient webClient = WebClient.builder()
                .baseUrl(NAVER_API_PREFIX)
                .defaultHeader(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + accessToken)
                .build();

        Map<String, Object> attributes = webClient.get()
                .uri(NAVER_INFORMATION_SUFFIX)
                .retrieve()
                .onStatus(UNAUTHORIZED::equals, response -> {
                    return Mono.error(new ApiException(ErrorType.NAVER_TOKEN_INVALID));
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
        return new NaverUserInfo(attributes);
    }
}
