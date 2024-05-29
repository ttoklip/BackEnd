package com.api.ttoklip.domain.member.repository;

import com.api.ttoklip.domain.member.domain.ProfileLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileLikeRepository extends JpaRepository<ProfileLike, Long>, ProfileLikeRepositoryCustom {
}
