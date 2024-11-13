package com.domain.honeytip.infrastructure;

import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipScrap;
import com.domain.honeytip.domain.HoneyTipScrapRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipScrapRepositoryImpl implements HoneyTipScrapRepository {

    private final HoneyTipScrapJpaRepository jpaRepository;
    private final HoneyTipScrapQueryRepository queryDSLRepository;

    @Override
    public Optional<HoneyTipScrap> findByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return jpaRepository.findByHoneyTipIdAndMemberId(honeyTipId, memberId);
    }

    @Override
    public boolean existsByHoneyTipIdAndMemberId(final Long honeyTipId, final Long memberId) {
        return jpaRepository.existsByHoneyTipIdAndMemberId(honeyTipId, memberId);
    }

    @Override
    public Long countHoneyTipScrapsByHoneyTipId(final Long postId) {
        return queryDSLRepository.countHoneyTipScrapByHoneyTipId(postId);
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void save(final HoneyTipScrap honeyTipScrap) {
        jpaRepository.save(honeyTipScrap);
    }

    @Override
    public Page<HoneyTip> getScrapPaging(final Long memberId, final Pageable pageable) {
        return queryDSLRepository.getScrapPaging(memberId, pageable);
    }
}
