package com.api.auth.oauth2.application;

import com.common.annotation.DistributedLock;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.member.domain.userInfo.OAuth2UserInfo;
import com.domain.member.domain.vo.Provider;
import com.domain.profile.application.ProfileService;
import com.domain.profile.domain.Profile;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OAuthMemberRegister {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    @DistributedLock(keyPrefix = "oauth-signup")
    public Member registerMember(final OAuth2UserInfo userInfo, final Provider provider) {
        String randomPassword = UUID.randomUUID().toString();
        String encodedPassword = encoder.encode(randomPassword);
        Member newMember = memberService.registerOAuthMember(userInfo, provider, encodedPassword);

        Profile profile = Profile.of(newMember, userInfo.getProfile());
        profileService.save(profile);

        return newMember;
    }
}
