package com.api.ttoklip.domain.profile.repository;

import com.api.ttoklip.domain.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
