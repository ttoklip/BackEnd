package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.ProfileLike;

public interface ProfileLikeRepositoryCustom {

    boolean isExists(Long fromMemberId, Long targetMemberId);

    ProfileLike findByFromMemberIdAndTargetMemberId(Long fromMemberId, Long targetMemberId);

    Long countProfileLikesByMemberId(Long targetMemberId);
}
