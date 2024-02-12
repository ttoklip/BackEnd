package com.api.ttoklip.global.security.auth.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.privacy.domain.Profile;
import com.api.ttoklip.domain.privacy.service.ProfileService;
import com.api.ttoklip.global.security.auth.dto.LoginRequest;
import com.api.ttoklip.global.security.auth.dto.LoginResponse;
import com.api.ttoklip.global.security.jwt.JwtProvider;
import com.api.ttoklip.global.security.auth.userInfo.OAuth2UserInfo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;
    private final JwtProvider jwtProvider;
    private final ProfileService profileService;

    public LoginResponse authenticate(final LoginRequest request) {
        String provider = request.getProvider();
        String accessToken = request.getAccessToken();

        OAuth2UserInfo userInfo = oAuth2UserInfoFactory.getUserInfo(provider, accessToken);
        String email = userInfo.getEmail();

        Optional<Member> memberOptional = memberService.findByEmailOptional(email);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return getLoginResponse(member, false);
        }

        Member member = registerNewMember(userInfo, provider);
        return getLoginResponse(member, true);
    }

    private LoginResponse getLoginResponse(final Member member, final boolean ifFirstLogin) {
        // Server JWT Token
        String jwtToken = jwtProvider.generateJwtToken(member.getEmail());
        return LoginResponse.builder()
                .jwtToken(jwtToken)
                .ifFirstLogin(ifFirstLogin)
                .build();
    }

    private Member registerNewMember(final OAuth2UserInfo userInfo, final String provider) {
        log.info("AuthService.registerNewMember");
        log.info("userInfo.getName() = " + userInfo.getName());
        Member newMember = Member.builder()
                .email(userInfo.getEmail())
                .originName(userInfo.getName())
                .provider(provider)
                .role(Role.CLIENT)
                .build();
        memberService.register(newMember);

        Profile profile = Profile.of(newMember, userInfo.getProfile());
        profileService.register(profile);

        return newMember;
    }
}
