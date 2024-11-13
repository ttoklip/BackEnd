package com.domain.honeytip.domain;

import com.domain.common.vo.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoneyTipRepository {
    HoneyTip findByIdActivated(Long honeyTipId);

    HoneyTip findHoneyTipWithDetails(Long postId);

    Page<HoneyTip> matchCategoryPaging(Category category, Pageable pageable);

    List<HoneyTip> findRecent3();

    List<HoneyTip> findHouseworkTips();

    List<HoneyTip> findRecipeTips();

    List<HoneyTip> findSafeLivingTips();

    List<HoneyTip> findWelfarePolicyTips();

    List<HoneyTip> getPopularityTop5();

    HoneyTip save(HoneyTip honeyTip);

    Page<HoneyTip> getContain(String keyword, Pageable pageable, String sort);
}
