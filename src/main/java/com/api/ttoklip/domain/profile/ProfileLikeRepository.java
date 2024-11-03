package com.api.ttoklip.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileLikeRepository extends JpaRepository<ProfileLike, Long>, ProfileLikeRepositoryCustom {
}
