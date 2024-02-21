package com.api.ttoklip.domain.honeytip.post.repository;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.question.post.domain.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HoneyTipRepositoryCustom {
    HoneyTip findByIdActivated(final Long honeyTipId);

    HoneyTip findByIdFetchJoin(final Long postId);

    List<HoneyTipComment> findActiveCommentsByHoneyTipId(final Long postId);

    Page<HoneyTip> matchCategoryPaging(Category category, Pageable pageable);

    List<HoneyTip> findRecent3();
}
