package com.api.ttoklip.domain.honeytip.post.repository;

import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import java.util.List;

public interface HoneyTipRepositoryCustom {
    HoneyTip findByIdActivated(final Long honeyTipId);

    HoneyTip findByIdFetchJoin(final Long postId);

    List<HoneyTipComment> findActiveCommentsByHoneyTipId(final Long postId);
}
