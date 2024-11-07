package com.api.ttoklip.domain.profile.infrastructure;

import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.profile.domain.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileJpaRepository jpaRepository;

    @Override
    public void save(final Profile profile) {
        jpaRepository.save(profile);
    }
}
