package com.api.ttoklip.domain.auth.controller;

import com.api.ttoklip.domain.auth.dto.response.AuthRes;
import com.api.ttoklip.domain.auth.dto.request.KakaoProfile;
import com.api.ttoklip.domain.auth.dto.request.NaverProfile;
import com.api.ttoklip.domain.auth.service.KakaoService;
import com.api.ttoklip.domain.auth.service.NaverService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Tag(name = "Authorization", description = "Authorization API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoService kakaoService;
    private final NaverService naverService;

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

    //네이버 code 발급
    @GetMapping("/naver-login")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        String url = naverService.getNaverAuthorizeUrl();
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //네이버 로그인
    @GetMapping("/naver/login")
    public SuccessResponse<AuthRes> naverCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        String accessToken = naverService.getNaverAccessToken(code, state);
        NaverProfile naverProfile = naverService.getNaverUserInfo(accessToken);
        System.out.println("naverProfile = " + naverProfile);
        return new SuccessResponse<>(naverService.naverLogin(naverProfile));
    }

}
