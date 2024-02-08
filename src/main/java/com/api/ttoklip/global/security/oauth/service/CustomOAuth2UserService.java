package com.api.ttoklip.global.security.oauth.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.profile.service.ProfileService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.security.oauth.principal.CustomOAuth2User;
import com.api.ttoklip.global.security.oauth.userInfo.KakaoUserInfo;
import com.api.ttoklip.global.security.oauth.userInfo.NaverUserInfo;
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

    /*
    // ToDo 관리자 기준 설정
        String name = oAuth2UserInfo.getName();
        if (email.equals("관리자")) {
            Role manager = Role.MANAGER;
        }
    */

        if (provider.equals("naver")) {
            Member member = findMemberByNaver(provider, oAuthAttributes);
            return CustomOAuth2User.of(member, attributes);
        }

        if (provider.equals("kakao")) {
            Member member = findMemberByKakao(provider, oAuthAttributes);
            return CustomOAuth2User.of(member, attributes);
        }

        throw new ApiException(ErrorType.OAUTH_INVALID_PROVIDER);
    }

    // -------------------------------------------- DB 조회 및 회원 가입 --------------------------------------------
    private Member findMemberByNaver(final String provider, final OAuth2UserInfo oAuthAttributes) {
        String email = ((NaverUserInfo) oAuthAttributes).getEmail();
        Optional<Member> memberOptional = memberRepository.findByNaverEmail(email);
        // 회원 정보가 없다면 회원가입 진행
        Member member = memberOptional.orElseGet(() -> registerMemberByNaver(oAuthAttributes, provider, email));
        return member;
    }

    private Member findMemberByKakao(final String provider, final OAuth2UserInfo oAuthAttributes) {
        Long kakaoId = ((KakaoUserInfo) oAuthAttributes).getKakaoId();
        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
        // 회원 정보가 없다면 회원가입 진행
        Member member = memberOptional.orElseGet(() -> registerMemberByKakao(oAuthAttributes, provider, kakaoId));
        return member;
    }

    // -------------------------------------------- DB 조회 및 회원 가입 끝 --------------------------------------------


    // -------------------------------------------- 회원가입 --------------------------------------------
    private Member registerMemberByNaver(final OAuth2UserInfo oAuth2UserInfo, final String provider,
                                         final String email) {
        String name = oAuth2UserInfo.getName();
        Member newMember = Member.builder()
                .name(name)
                .naverEmail(email)
                .provider(provider)
                .role(Role.CLIENT)
                .build();
        memberRepository.save(newMember); // 회원 저장

        registerProfile(oAuth2UserInfo, newMember);
        return newMember;
    }

    private Member registerMemberByKakao(final OAuth2UserInfo oAuth2UserInfo, final String provider,
                                         final Long kakaoId) {
        String name = oAuth2UserInfo.getName();
        Member newMember = Member.builder()
                .name(name)
                .kakaoId(kakaoId)
                .provider(provider)
                .role(Role.CLIENT)
                .build();
        memberRepository.save(newMember); // 회원 저장

        registerProfile(oAuth2UserInfo, newMember);
        return newMember;
    }

    // -------------------------------------------- 회원가입 끝 --------------------------------------------


    // -------------------------------------------- 프로필 생성 --------------------------------------------
    private void registerProfile(final OAuth2UserInfo oAuth2UserInfo, final Member newMember) {
        // 프로필 생성 및 저장
        String profileImageUrl = oAuth2UserInfo.getProfile();
        Profile newProfile = Profile.of(newMember, profileImageUrl);
        profileService.register(newProfile);
    }
    // -------------------------------------------- 프로필 생성 --------------------------------------------
}