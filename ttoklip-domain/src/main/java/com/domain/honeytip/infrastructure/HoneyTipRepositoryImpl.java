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

    private final HoneyTipJpaRepository jpaRepository;
//    private final HoneyTipQueryRepository queryDSLRepository;

    @Override
    public HoneyTip save(final HoneyTip honeyTip) {
        return jpaRepository.save(honeyTip);
    }

    @Override
    public Page<HoneyTip> getContain(final String keyword, final Pageable pageable, final String sort) {
//        return queryDSLRepository.getContain(keyword, pageable, sort);
        return null;
    }

    @Override
    public Page<HoneyTip> matchWriterPaging(final Long targetId, final Pageable pageable) {
//        return queryDSLRepository.matchWriterPaging(targetId, pageable);
        return null;
    }

    @Override
    public HoneyTip findByIdActivated(final Long honeyTipId) {
//        return queryDSLRepository.findByIdActivated(honeyTipId);
        return null;
    }

    @Override
    public HoneyTip findHoneyTipWithDetails(final Long postId) {
//        return queryDSLRepository.findHoneyTipWithDetails(postId);
        return null;
    }

    @Override
    public Page<HoneyTip> matchCategoryPaging(final Category category, final Pageable pageable) {
//        return queryDSLRepository.matchCategoryPaging(category, pageable);
        return null;
    }

    @Override
    public List<HoneyTip> findRecent3() {
//        return queryDSLRepository.findRecent3();
        return null;
    }

    @Override
    public List<HoneyTip> findHouseworkTips() {
//        return queryDSLRepository.findHouseworkTips();
        return null;
    }

    @Override
    public List<HoneyTip> findRecipeTips() {
//        return queryDSLRepository.findRecipeTips();
        return null;
    }

    @Override
    public List<HoneyTip> findSafeLivingTips() {
//        return queryDSLRepository.findSafeLivingTips();
        return null;
    }

    @Override
    public List<HoneyTip> findWelfarePolicyTips() {
//        return queryDSLRepository.findWelfarePolicyTips();
        return null;
    }

    @Override
    public List<HoneyTip> getPopularityTop5() {
//        return queryDSLRepository.getPopularityTop5();
        return null;
    }
}
