package com.api.ttoklip.global.security.oauth2.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.vo.Provider;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.security.oauth2.dto.OAuthLoginRequest;
import com.api.ttoklip.global.security.oauth2.dto.OAuthLoginResponse;
import com.api.ttoklip.global.security.oauth2.userInfo.OAuth2UserInfo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final OAuthRegisterService oAuthRegisterService;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;

    public OAuthLoginResponse authenticate(final OAuthLoginRequest request) {
        Provider provider = request.getProvider();
        String accessToken = request.getAccessToken();

        OAuth2UserInfo userInfo = oAuth2UserInfoFactory.getUserInfo(provider.getType(), accessToken);
        String email = userInfo.getEmail();

        Optional<Member> memberOptional = memberService.findByEmailOptional(email);
        if (memberOptional.isPresent()) {
            // 이미 우리 회원일 때
            Member member = memberOptional.get();
            return alreadyOurUser(member);
        }

        // 회원가입
        Member member = oAuthRegisterService.registerNewMember(userInfo, provider);
        return getLoginResponse(member, true);
    }

    private OAuthLoginResponse alreadyOurUser(final Member member) {
        String nickname = member.getNickname();
        if (nickname == null) {
            // 회원가입은 했지만 개인정보(프로필 사진, 닉네임, 독립 경력)을 입력하지 않았을 때 로그인처리, FirstLogin true 처리하여 개인정보 유도
            return getLoginResponse(member, true);
        }
        // 로그인
        return getLoginResponse(member, false);
    }

    private OAuthLoginResponse getLoginResponse(final Member member, final boolean ifFirstLogin) {
        // Server JWT Token
        String jwtToken = jwtProvider.generateJwtToken(member.getEmail());
        return OAuthLoginResponse.builder()
                .jwtToken(jwtToken)
                .ifFirstLogin(ifFirstLogin)
                .build();
    }
}
