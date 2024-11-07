package com.api.ttoklip.domain.profile.infrastructure;

import com.api.ttoklip.domain.profile.domain.ProfileLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileLikeJpaRepository extends JpaRepository<ProfileLike, Long> {
}
