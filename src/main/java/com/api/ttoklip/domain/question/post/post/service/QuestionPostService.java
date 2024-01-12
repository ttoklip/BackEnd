package com.api.ttoklip.domain.question.post.post.service;

import com.api.ttoklip.domain.question.post.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.post.dto.request.QuestionEditRequest;
import com.api.ttoklip.domain.question.post.post.dto.response.QuestionWithCommentResponse;
import org.springframework.stereotype.Service;

@Service
public class QuestionPostService {
    public Long register(final QuestionCreateRequest request) {
        return null;
    }

    public void delete(final Long postId) {
    }

    public QuestionWithCommentResponse getSinglePost(final Long postId) {
        return null;
    }

    public void edit(final Long postId, final QuestionEditRequest request) {
    }
}
