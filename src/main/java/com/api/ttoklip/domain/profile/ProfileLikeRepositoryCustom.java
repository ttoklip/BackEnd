package com.api.ttoklip.domain.profile;

public interface ProfileLikeRepositoryCustom {

    boolean isExists(Long fromMemberId, Long targetMemberId);

    ProfileLike findByFromMemberIdAndTargetMemberId(Long fromMemberId, Long targetMemberId);

    Long countProfileLikesByMemberId(Long targetMemberId);
}
