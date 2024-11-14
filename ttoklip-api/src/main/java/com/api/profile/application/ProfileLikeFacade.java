package com.api.profile.application;

import com.api.global.support.response.Message;
import com.api.profile.presentation.TargetMemberProfileResponse;
import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.profile.application.ProfileLikeService;
import com.domain.profile.application.response.TargetMemberProfile;
import com.domain.profile.domain.ProfileLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileLikeFacade {

    private final MemberService memberService;
    private final ProfileLikeService profileLikeService;

    @Transactional
    public Message registerProfileLike(final Long targetMemberId, final Long memberId) {
        validLikeMyself(targetMemberId, memberId);

        Member targetMember = memberService.getById(targetMemberId);

        boolean isExists = profileLikeService.isExists(memberId, targetMemberId);
        if (isExists) {
            return Message.registerProfileLike(targetMemberId);
        }

        Member member = memberService.getById(memberId);
        ProfileLike profileLike = ProfileLike.of(member, targetMember);
        profileLikeService.save(profileLike);

        return Message.registerProfileLike(targetMemberId);
    }

    @Transactional
    public Message cancelProfileLike(final Long targetMemberId, final Long memberId) {
        validLikeMyself(targetMemberId, memberId);

        Member targetMember = memberService.getById(targetMemberId);

        ProfileLike profileLike = profileLikeService.findByFromMemberIdAndTargetMemberId(
                memberId, targetMember.getId()
        );

        profileLikeService.deleteById(profileLike.getId());
        return Message.registerProfileLike(targetMemberId);
    }

    private void validLikeMyself(final Long targetMemberId, final Long currentMemberId) {
        if (currentMemberId.equals(targetMemberId)) {
            throw new ApiException(ErrorType.Profile_LIKE_MYSELF);
        }
    }

    public TargetMemberProfileResponse getTargetMemberProfile(final Long targetMemberId) {
        TargetMemberProfile response = memberService.getTargetMemberProfile(targetMemberId);
        return TargetMemberProfileResponse.from(response);
    }
}
