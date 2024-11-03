package com.api.ttoklip.domain.profile;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileLikeService {

    private final ProfileLikeRepository profileLikeRepository;
    private final MemberService memberService;

    @Transactional
    public Message registerProfileLike(final Long targetMemberId) {
        Member currentMember = getCurrentMember();
        validLikeMyself(targetMemberId, currentMember);

        Member targetMember = memberService.findById(targetMemberId);

        boolean isExists = profileLikeRepository.isExists(currentMember.getId(), targetMemberId);
        if (isExists) {
            return Message.registerProfileLike(targetMemberId);
        }

        ProfileLike profileLike = ProfileLike.of(currentMember, targetMember);
        profileLikeRepository.save(profileLike);

        return Message.registerProfileLike(targetMemberId);
    }

    @Transactional
    public Message cancelProfileLike(final Long targetMemberId) {
        Member currentMember = getCurrentMember();
        validLikeMyself(targetMemberId, currentMember);

        Member targetMember = memberService.findById(targetMemberId);

        ProfileLike profileLike = profileLikeRepository.findByFromMemberIdAndTargetMemberId(
                currentMember.getId(), targetMember.getId()
        );

        profileLikeRepository.deleteById(profileLike.getId());
        return Message.registerProfileLike(targetMemberId);
    }

    private void validLikeMyself(final Long targetMemberId, final Member currentMember) {
        if (currentMember.getId().equals(targetMemberId)) {
            throw new ApiException(ErrorType.Profile_LIKE_MYSELF);
        }
    }

}
