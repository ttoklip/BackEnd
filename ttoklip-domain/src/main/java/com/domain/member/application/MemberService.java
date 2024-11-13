package com.domain.member.application;

import static com.common.exception.ErrorType._USER_NOT_FOUND_BY_TOKEN;
import static com.common.exception.ErrorType._USER_NOT_FOUND_DB;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.application.response.MemberStreetResponse;
import com.domain.member.domain.LocalMemberCreate;
import com.domain.member.domain.Member;
import com.domain.member.domain.MemberRepository;
import com.domain.interest.application.response.InterestResponse;
import com.domain.member.domain.userInfo.OAuth2UserInfo;
import com.domain.member.domain.vo.Provider;
import com.domain.member.domain.vo.Role;
import com.domain.profile.application.response.TargetMemberProfile;
import com.domain.profile.domain.Profile;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(_USER_NOT_FOUND_DB));
    }

    public Member findByIdWithProfile(final Long memberId) {
        return memberRepository.findByIdWithProfile(memberId);
    }

    public Member findByNickNameWithProfile(final String nickName) {
        return memberRepository.findByNickNameWithProfile(nickName);
    }

    public boolean isExistsNickname(final String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(_USER_NOT_FOUND_BY_TOKEN));
    }

    public Optional<Member> findByEmailOptional(final String email) {
        return memberRepository.findByEmail(email);

    }

    @Transactional
    public void register(final Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void updateMemberFCMToken(final Member member, final String fcmToken) {
        Member currentMember = getById(member.getId());
        currentMember.updateFcmToken(fcmToken);
    }

    public TargetMemberProfile getTargetMemberProfile(final Long targetMemberId) {
        Member member = memberRepository.getTargetMemberProfile(targetMemberId);
        validProfile(member.getProfile());
        List<InterestResponse> interests = member.getInterests().stream()
                .map(InterestResponse::from)
                .toList();

        return TargetMemberProfile.of(member, member.getProfileLikesFrom().size(), interests);
    }

    private void validProfile(final Profile profile) {
        if (profile == null) {
            throw new ApiException(ErrorType.Profile_NOT_FOUND);
        }
    }

    public MemberStreetResponse getMemberStreet(final Member member) {
        Member findMember = getById(member.getId());
        return MemberStreetResponse.of(findMember.getStreet());
    }

    @Transactional
    public void updateMemberIndependenceDates() {
        List<Member> members = memberRepository.findAll()
                .stream()
                .peek(Member::incrementMonth)
                .toList();

        memberRepository.saveAll(members);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member registerLocalMember(final LocalMemberCreate create) {
        String email = create.email();
        validateEmail(email);
        String password = create.password();
        String originName = create.originName();
        String nickname = create.nickname();
        int independentYear = create.independentYear();
        int independentMonth = create.independentMonth();
        String street = create.street();

        Member newMember = Member.builder()
                .email(email)
                .password(password)
                .originName(originName)
                .nickname(nickname)
                .role(Role.CLIENT)
                .provider(Provider.LOCAL)
                .independentYear(independentYear)
                .independentMonth(independentMonth)
                .street(street)
                .build();

        memberRepository.save(newMember);
        return newMember;
    }

    public void validateEmail(final String email) {
        boolean isExist = memberRepository.existsByEmail(email);

        if (isExist) {
            throw new ApiException(ErrorType.ALREADY_EXISTS_JOIN_ID);
        }
    }

    public Member registerOAuthMember(final OAuth2UserInfo userInfo, final Provider provider, final String encodedPassword) {
        Member newMember = Member.builder()
                .email(userInfo.getEmail())
                .originName(userInfo.getName())
                .provider(provider)
                .role(Role.CLIENT)
                .password(encodedPassword)
                .build();
        memberRepository.save(newMember);
        return newMember;
    }
}
