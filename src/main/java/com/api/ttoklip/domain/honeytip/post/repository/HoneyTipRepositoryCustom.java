package com.api.ttoklip.domain.honeytip.post.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoneyTipRepositoryCustom {
    HoneyTip findByIdActivated(final Long honeyTipId);

    HoneyTip findHoneyTipWithDetails(final Long postId);

    Page<HoneyTip> matchCategoryPaging(Category category, Pageable pageable);

    List<HoneyTip> findRecent3();

    List<HoneyTip> findHouseworkTips();

    List<HoneyTip> findRecipeTips();

    List<HoneyTip> findSafeLivingTips();

    List<HoneyTip> findWelfarePolicyTips();

    List<HoneyTip> getPopularityTop5();
}
