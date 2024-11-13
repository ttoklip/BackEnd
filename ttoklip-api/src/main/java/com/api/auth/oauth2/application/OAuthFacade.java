package com.api.auth.oauth2.application;

import com.api.auth.oauth2.presentation.OAuthLogin;
import com.api.auth.oauth2.presentation.OAuthLoginResponse;
import com.domain.member.domain.userInfo.OAuth2UserInfo;
import com.common.jwt.TokenProvider;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.member.domain.vo.Provider;
import com.domain.profile.application.ProfileService;
import com.domain.profile.domain.Profile;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthFacade {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final TokenProvider provider;
    private final BCryptPasswordEncoder encoder;
    private final OAuth2InfoAdapter oAuth2InfoAdapter;

    public OAuthLoginResponse authenticate(final OAuthLogin request) {
        Provider provider = request.getProvider();
        String accessToken = request.accessToken();

        OAuth2UserInfo userInfo = oAuth2InfoAdapter.getUserInfo(provider.getType(), accessToken);
        String email = userInfo.getEmail();

        Optional<Member> memberOptional = memberService.findByEmailOptional(email);
        if (memberOptional.isPresent()) {
            // 이미 우리 회원일 때
            Member member = memberOptional.get();
            return alreadyOurUser(member);
        }

        // 회원가입
        Member member = registerMember(userInfo, provider);
        return getLoginResponse(member, true);
    }

    public Member registerMember(final OAuth2UserInfo userInfo, final Provider provider) {
        String randomPassword = UUID.randomUUID().toString();
        String encodedPassword = encoder.encode(randomPassword);
        Member newMember = memberService.registerOAuthMember(userInfo, provider, encodedPassword);

        Profile profile = Profile.of(newMember, userInfo.getProfile());
        profileService.save(profile);

        return newMember;
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
        String jwtToken = provider.create(member.getEmail());
        return OAuthLoginResponse.builder()
                .jwtToken(jwtToken)
                .ifFirstLogin(ifFirstLogin)
                .build();
    }
}
