package com.api.ttoklip.domain.question.post.comment.service;

import com.api.ttoklip.domain.question.post.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.question.post.comment.dto.request.CommentEditRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionCommentService {
    public Long register(final Long postId, final CommentCreateRequest request) {
        return null;
    }

    public void edit(final Long commentId, final CommentEditRequest commentEditRequest) {
    }

    public void delete(final Long commentId) {
    }
}
