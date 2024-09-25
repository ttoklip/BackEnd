package com.api.ttoklip.domain.member.service;

import static com.api.ttoklip.global.exception.ErrorType._USER_NOT_FOUND_BY_TOKEN;
import static com.api.ttoklip.global.exception.ErrorType._USER_NOT_FOUND_DB;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.dto.response.MemberStreetResponse;
import com.api.ttoklip.domain.member.dto.response.TargetMemberProfile;
import com.api.ttoklip.domain.member.repository.MemberOAuthRepository;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import com.api.ttoklip.domain.privacy.domain.Profile;
import com.api.ttoklip.domain.privacy.dto.InterestResponse;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
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
    private final MemberOAuthRepository memberOAuthRepository;

    private static final String SEOUL = "서울특별시";

    public Member findById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(_USER_NOT_FOUND_DB));
    }

    public Member findByIdWithProfile(final Long memberId) {
        return memberOAuthRepository.findByIdWithProfile(memberId);
    }

    public Member findByNickNameWithProfile(final String nickName) {
        return memberOAuthRepository.findByNickNameWithProfile(nickName);//02.17
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
    public Message updateMemberFCMToken(final Member member, final String fcmToken) {
        Member currentMember = findById(member.getId());
        currentMember.updateFcmToken(fcmToken);
        return Message.updateFCM();
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
        return MemberStreetResponse.of(findMember.getStreet(), !findMember.getStreet().startsWith(SEOUL));
    }
}
