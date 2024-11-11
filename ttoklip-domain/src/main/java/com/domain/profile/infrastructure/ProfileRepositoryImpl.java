package com.domain.profile.infrastructure;

import com.domain.profile.domain.Profile;
import com.domain.profile.domain.ProfileRepository;
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
