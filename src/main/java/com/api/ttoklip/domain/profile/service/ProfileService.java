package com.api.ttoklip.domain.profile.service;

import com.api.ttoklip.domain.profile.domain.Profile;
import com.api.ttoklip.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public Long register(final Profile profile) {
        profileRepository.save(profile);
        return profile.getId();
    }
}
