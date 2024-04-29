package com.api.ttoklip.domain.member.repository;

public interface ProfileLikeRepositoryCustom {

    boolean isExists(Long fromMemberId, Long targetMemberId);
}
