package com.domain.honeytip.infrastructure;

import com.domain.common.vo.Category;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoneyTipRepositoryImpl implements HoneyTipRepository {

    private final HoneyTipJpaRepository honeyTipJpaRepository;
    private final HoneyTipQueryRepository honeyTipQueryRepository;

    @Override
    public HoneyTip save(final HoneyTip honeyTip) {
        return honeyTipJpaRepository.save(honeyTip);
    }

    @Override
    public HoneyTip findByIdActivated(final Long honeyTipId) {
        return honeyTipQueryRepository.findByIdActivated(honeyTipId);
    }

    @Override
    public HoneyTip findHoneyTipWithDetails(final Long postId) {
        return honeyTipQueryRepository.findHoneyTipWithDetails(postId);
    }

    @Override
    public Page<HoneyTip> matchCategoryPaging(final Category category, final Pageable pageable) {
        return honeyTipQueryRepository.matchCategoryPaging(category, pageable);
    }

    @Override
    public List<HoneyTip> findRecent3() {
        return honeyTipQueryRepository.findRecent3();
    }

    @Override
    public List<HoneyTip> findHouseworkTips() {
        return honeyTipQueryRepository.findHouseworkTips();
    }

    @Override
    public List<HoneyTip> findRecipeTips() {
        return honeyTipQueryRepository.findRecipeTips();
    }

    @Override
    public List<HoneyTip> findSafeLivingTips() {
        return honeyTipQueryRepository.findSafeLivingTips();
    }

    @Override
    public List<HoneyTip> findWelfarePolicyTips() {
        return honeyTipQueryRepository.findWelfarePolicyTips();
    }

    @Override
    public List<HoneyTip> getPopularityTop5() {
        return honeyTipQueryRepository.getPopularityTop5();
    }
}
