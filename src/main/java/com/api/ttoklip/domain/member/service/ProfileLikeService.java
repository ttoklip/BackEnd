package com.api.ttoklip.domain.member.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.ProfileLike;
import com.api.ttoklip.domain.member.repository.ProfileLikeRepository;
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
    public void registerProfileLike(final Long targetMemberId) {
        Member targetMember = memberService.findById(targetMemberId);
        Member currentMember = getCurrentMember();

        boolean isExists = profileLikeRepository.isExists(currentMember.getId(), targetMemberId);
        if (isExists) {
            return;
        }

        ProfileLike profileLike = ProfileLike.of(currentMember, targetMember);
        profileLikeRepository.save(profileLike);
    }

    @Transactional
    public void cancelProfileLike(final Long targetMemberId) {
        Member targetMember = memberService.findById(targetMemberId);
        Member currentMember = getCurrentMember();

        ProfileLike profileLike = profileLikeRepository.findByFromMemberIdAndTargetMemberId(
                currentMember.getId(), targetMember.getId()
        );

        profileLikeRepository.deleteById(profileLike.getId());
    }

}
