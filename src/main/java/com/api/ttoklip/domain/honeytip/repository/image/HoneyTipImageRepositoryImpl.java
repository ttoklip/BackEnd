package com.api.ttoklip.domain.honeytip.repository.image;

import com.api.ttoklip.domain.honeytip.domain.HoneyTipImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipImageRepositoryImpl implements HoneyTipImageRepository {

    private final HoneyTipImageJpaRepository honeyTipImageJpaRepository;
    private final HoneyTipImageQueryRepository honeyTipImageQueryRepository;

    @Override
    public HoneyTipImage save(final HoneyTipImage honeyTipImage) {
        return honeyTipImageJpaRepository.save(honeyTipImage);
    }

    @Override
    public boolean existsByHoneyTipIdAndUrl(final Long honeyTipId, final String url) {
        return honeyTipImageJpaRepository.existsByHoneyTipIdAndUrl(honeyTipId, url);
    }

    @Override
    public void verifyMemberIsImageOwner(final List<Long> imageIds, final Long memberId) {
        honeyTipImageQueryRepository.verifyMemberIsImageOwner(imageIds, memberId);
    }

    @Override
    public boolean doAllImageIdsExist(final List<Long> imageIds) {
        return honeyTipImageQueryRepository.doAllImageIdsExist(imageIds);
    }

    @Override
    public void deleteByImageIds(final List<Long> imageIds) {
        honeyTipImageQueryRepository.deleteByImageIds(imageIds);
    }
}
