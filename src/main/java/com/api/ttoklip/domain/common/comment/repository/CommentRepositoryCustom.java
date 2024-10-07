package com.api.ttoklip.domain.common.comment.repository;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.honeytip.domain.HoneyTipComment;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {
    Comment findByIdActivated(final Long commentId);

    Optional<Comment> findByIdActivatedOptional(final Long commentId);

    List<HoneyTipComment> findCommentsByHoneyTipId(final Long honeyTipId);

    List<NewsletterComment> findCommentsByNewsletterId(final Long newsletterId);
}

