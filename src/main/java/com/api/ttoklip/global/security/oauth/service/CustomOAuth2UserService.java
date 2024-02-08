package com.api.ttoklip.global.security.oauth.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.profile.service.ProfileService;
import com.api.ttoklip.global.security.oauth.principal.CustomOAuth2User;
import com.api.ttoklip.global.security.oauth.userInfo.OAuth2UserInfo;
import com.api.ttoklip.global.security.oauth.userInfo.OAuthUserInfoFactory;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final ProfileService profileService;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("------------------ getAttributes : {}", attributes);

        String provider = request.getClientRegistration().getRegistrationId(); // 예: kakao, naver

        OAuth2UserInfo oAuthAttributes = OAuthUserInfoFactory.getOAuthAttributes(provider, attributes);

        // 회원가입 유무 확인
        String email = oAuthAttributes.getEmail();
        Optional<Member> memberOptional = memberRepository.findMemberByEmailAndProvider(email, provider);

        // 회원 정보가 없다면 회원가입 진행
        Member member = memberOptional.orElseGet(() -> registerNewMember(oAuthAttributes, provider, email));

        // CustomOAuth2User 객체 생성 및 반환
        return CustomOAuth2User.of(member, attributes);
    }

    private Member registerNewMember(final OAuth2UserInfo oAuth2UserInfo,
                                     final String provider, final String email) {
        String name = oAuth2UserInfo.getName();

        Member newMember = Member.builder()
                .userNickname(name)
                .email(email)
                .provider(provider)
                .role(Role.CLIENT)
                .build();

        memberRepository.save(newMember); // 회원 저장

        // 프로필 생성 및 저장
        String profileImageUrl = oAuth2UserInfo.getProfile();
        Profile newProfile = Profile.of(newMember, profileImageUrl);
        profileService.register(newProfile);

        return newMember;
    }
}
