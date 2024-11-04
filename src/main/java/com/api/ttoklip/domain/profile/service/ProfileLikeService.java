package com.api.ttoklip.domain.profile.service;

import com.api.ttoklip.domain.profile.domain.ProfileLike;
import com.api.ttoklip.domain.profile.domain.ProfileLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileLikeService {

    private final ProfileLikeRepository profileLikeRepository;

    public boolean isExists(final Long fromMemberId, final Long targetMemberId) {
        return profileLikeRepository.isExists(fromMemberId, targetMemberId);
    }

    public ProfileLike findByFromMemberIdAndTargetMemberId(final Long fromMemberId, final Long targetMemberId) {
        return profileLikeRepository.findByFromMemberIdAndTargetMemberId(fromMemberId, targetMemberId);
    }

    @Transactional
    public void save(final ProfileLike profileLike) {
        profileLikeRepository.save(profileLike);
    }

    @Transactional
    public void deleteById(final Long id) {
        profileLikeRepository.deleteById(id);
    }
}
