package com.domain.profile.infrastructure;

import com.domain.profile.domain.ProfileLike;
import com.domain.profile.domain.ProfileLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileLikeRepositoryImpl implements ProfileLikeRepository {

    private final ProfileLikeJpaRepository jpaRepository;
//    private final ProfileLikeQueryRepository queryDSLRepository;


    @Override
    public boolean isExists(final Long fromMemberId, final Long targetMemberId) {
//        return queryDSLRepository.isExists(fromMemberId, targetMemberId);
        return false;
    }

    @Override
    public void save(final ProfileLike profileLike) {
        jpaRepository.save(profileLike);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public ProfileLike findByFromMemberIdAndTargetMemberId(final Long fromMemberId, final Long targetMemberId) {
//        return queryDSLRepository.findByFromMemberIdAndTargetMemberId(fromMemberId, targetMemberId);
        return null;
    }

    @Override
    public Long countProfileLikesByMemberId(final Long targetMemberId) {
//        return queryDSLRepository.countProfileLikesByMemberId(targetMemberId);
        return null;
    }
}
