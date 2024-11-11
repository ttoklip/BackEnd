package com.domain.member.application;

import static com.common.exception.ErrorType._USER_NOT_FOUND_BY_TOKEN;
import static com.common.exception.ErrorType._USER_NOT_FOUND_DB;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.application.response.MemberStreetResponse;
import com.domain.member.domain.Member;
import com.domain.member.domain.MemberRepository;
import com.domain.interest.application.response.InterestResponse;
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

    public Member findById(final Long memberId) {
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
        Member currentMember = findById(member.getId());
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
        Member findMember = findById(member.getId());
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


}
