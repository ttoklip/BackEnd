package com.domain.comment.domain;

import com.common.Filterable;

public record CommentCreate(String comment, Long parentCommentId) implements Filterable {

    @Override
    public String getFilterContent() {
        return comment();
    }
}
