package com.api.ttoklip.domain.profile.facade;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.domain.profile.controller.response.TargetMemberProfile;
import com.api.ttoklip.domain.profile.domain.ProfileLike;
import com.api.ttoklip.domain.profile.service.ProfileLikeService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
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
    public Message registerProfileLike(final Long targetMemberId) {
        Member currentMember = getCurrentMember();
        validLikeMyself(targetMemberId, currentMember);

        Member targetMember = memberService.findById(targetMemberId);

        boolean isExists = profileLikeService.isExists(currentMember.getId(), targetMemberId);
        if (isExists) {
            return Message.registerProfileLike(targetMemberId);
        }

        ProfileLike profileLike = ProfileLike.of(currentMember, targetMember);
        profileLikeService.save(profileLike);

        return Message.registerProfileLike(targetMemberId);
    }

    @Transactional
    public Message cancelProfileLike(final Long targetMemberId) {
        Member currentMember = getCurrentMember();
        validLikeMyself(targetMemberId, currentMember);

        Member targetMember = memberService.findById(targetMemberId);

        ProfileLike profileLike = profileLikeService.findByFromMemberIdAndTargetMemberId(
                currentMember.getId(), targetMember.getId()
        );

        profileLikeService.deleteById(profileLike.getId());
        return Message.registerProfileLike(targetMemberId);
    }

    private void validLikeMyself(final Long targetMemberId, final Member currentMember) {
        if (currentMember.getId().equals(targetMemberId)) {
            throw new ApiException(ErrorType.Profile_LIKE_MYSELF);
        }
    }

    public TargetMemberProfile getTargetMemberProfile(final Long targetMemberId) {
        return memberService.getTargetMemberProfile(targetMemberId);
    }
}
