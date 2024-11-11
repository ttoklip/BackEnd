package com.domain.profile.infrastructure;

import com.domain.profile.domain.ProfileLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileLikeJpaRepository extends JpaRepository<ProfileLike, Long> {
}
