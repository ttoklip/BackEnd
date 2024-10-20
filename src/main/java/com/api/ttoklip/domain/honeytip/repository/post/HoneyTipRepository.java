package com.api.ttoklip.domain.honeytip.repository.post;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
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
}
