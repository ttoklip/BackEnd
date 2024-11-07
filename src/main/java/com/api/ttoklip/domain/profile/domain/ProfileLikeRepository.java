package com.api.ttoklip.domain.profile.domain;

public interface ProfileLikeRepository {
    void save(ProfileLike profileLike);

    void deleteById(Long id);

    boolean isExists(Long fromMemberId, Long targetMemberId);

    ProfileLike findByFromMemberIdAndTargetMemberId(Long fromMemberId, Long targetMemberId);

    Long countProfileLikesByMemberId(Long targetMemberId);
}
