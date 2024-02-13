package com.api.ttoklip.global.security.auth.service;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.auth.userInfo.KakaoUserInfo;
import com.api.ttoklip.global.security.auth.userInfo.NaverUserInfo;
import com.api.ttoklip.global.security.auth.userInfo.OAuth2UserInfo;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getUserInfo(String provider, String accessToken) {

        if (provider.equals("kakao")) {
            log.info("------------------ 카카오 로그인 요청");
            return getKakaoUserInfo(accessToken);
        }

        if (provider.equals("naver")) {
            log.info("------------------ 네이버 로그인 요청");
            return getNaverUserInfo(accessToken);
        }

        log.error("provider가 올바르지 않습니다.");

        throw new ApiException(ErrorType.OAUTH_INVALID_PROVIDER);
    }

    private KakaoUserInfo getKakaoUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();

        Map<String, Object> attributes = webClient.get()
                .uri("/v2/user/me")
                .retrieve()
                .onStatus(UNAUTHORIZED::equals, response -> {
                    // 401 오류 처리
                    return Mono.error(new ApiException(ErrorType.KAKAO_TOKEN_INVALID));
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        log.info("---------------------------------------- attributes = " + attributes);
        return new KakaoUserInfo(attributes);
    }

    private OAuth2UserInfo getNaverUserInfo(final String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();

        Map<String, Object> attributes = webClient.get()
                .uri("/v1/nid/me")
                .retrieve()
                .onStatus(UNAUTHORIZED::equals, response -> {
                    // 401 오류 처리
                    return Mono.error(new ApiException(ErrorType.NAVER_TOKEN_INVALID));
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        log.info("---------------------------------------- naver attributes = " + attributes);
        return new NaverUserInfo(attributes);
    }
}
