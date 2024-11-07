package com.api.ttoklip.domain.profile.infrastructure;

import com.api.ttoklip.domain.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<Profile, Long> {
}
