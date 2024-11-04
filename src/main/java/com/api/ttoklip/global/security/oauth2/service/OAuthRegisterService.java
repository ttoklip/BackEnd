package com.api.ttoklip.global.security.oauth2.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.vo.Provider;
import com.api.ttoklip.domain.member.domain.vo.Role;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.profile.service.ProfileService;
import com.api.ttoklip.global.security.oauth2.userInfo.OAuth2UserInfo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthRegisterService {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member registerNewMember(final OAuth2UserInfo userInfo, final Provider provider) {
        log.info("OAuthService.registerNewMember");
        log.info("userInfo.getName() = {}", userInfo.getName());

        // 소셜 로그인 사용자의 비밀번호에 임의의 해시 값 설정
        String randomPassword = UUID.randomUUID().toString();
        String encodedPassword = bCryptPasswordEncoder.encode(randomPassword);

        Member newMember = Member.builder()
                .email(userInfo.getEmail())
                .originName(userInfo.getName())
                .provider(provider)
                .role(Role.CLIENT)
                .password(encodedPassword)
                .build();
        memberService.register(newMember);

        Profile profile = Profile.of(newMember, userInfo.getProfile());
        profileService.register(profile);

        return newMember;
    }
}
