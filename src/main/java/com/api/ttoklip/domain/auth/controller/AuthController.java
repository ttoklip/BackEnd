package com.api.ttoklip.domain.auth.controller;

import com.api.ttoklip.domain.auth.dto.AuthRes;
import com.api.ttoklip.domain.auth.dto.KakaoProfile;
import com.api.ttoklip.domain.auth.service.KakaoService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "Authorization", description = "Authorization API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoService kakaoService;

    // 카카오 code 발급, 로그인 인증으로 리다이렉트해주는 url
    @GetMapping("/login")
    public void socialLoginRedirect() throws IOException {
        kakaoService.accessRequest();
    }


    //카카오 로그인
    @GetMapping("/kakao/login")
    public SuccessResponse<AuthRes> kakaoCallback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getKakaoAccessToken(code);
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);

        return new SuccessResponse<>(kakaoService.kakaoLogin(kakaoProfile));
    }
}
