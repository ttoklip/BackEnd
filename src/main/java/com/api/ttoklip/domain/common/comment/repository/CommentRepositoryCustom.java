package com.api.ttoklip.domain.common.comment.repository;

import com.api.ttoklip.domain.common.comment.Comment;

public interface CommentRepositoryCustom {
    Comment findByIdActivated(final Long commentId);
}
