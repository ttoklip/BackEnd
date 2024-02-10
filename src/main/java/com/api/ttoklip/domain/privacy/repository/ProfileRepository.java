package com.api.ttoklip.domain.privacy.repository;

import com.api.ttoklip.domain.privacy.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
