package com.api.ttoklip.domain.common.comment.repository;

import com.api.ttoklip.domain.common.comment.Comment;
import java.util.Optional;

public interface CommentRepositoryCustom {
    Comment findByIdActivated(final Long commentId);

    Optional<Comment> findByIdActivatedOptional(final Long commentId);
}
