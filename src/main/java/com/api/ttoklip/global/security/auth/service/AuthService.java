package com.api.ttoklip.global.security.auth.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.security.auth.dto.LoginRequest;
import com.api.ttoklip.global.security.auth.dto.LoginResponse;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.security.oauth.userInfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    private final JwtProvider jwtProvider;

    public LoginResponse authenticate(final LoginRequest request) {
        String provider = request.getProvider();
        String accessToken = request.getAccessToken();

        OAuth2UserInfo userInfo = oAuth2UserInfoFactory.getUserInfo(provider, accessToken);
        String email = userInfo.getEmail();

        Member member = memberService.findByEmailOptional(email)
//                .orElseGet() // 회원가입 or 로그인 처리

        // Server JWT Token
        String jwtToken = jwtProvider.generateJwtToken(member.getEmail());

        return LoginResponse.builder()
                .jwtToken(jwtToken)
//                .ifFirstLogin()
                .build();
    }
}
