package com.api.ttoklip.domain.town.community.comment.service;

import com.api.ttoklip.domain.town.community.comment.dto.request.CommCommentCreateRequest;
import com.api.ttoklip.domain.town.community.comment.dto.request.CommCommentUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class CommCommentService {
    public Long createCommComment(Long commId, CommCommentCreateRequest request) {
        return null;
    }

    public static void updateCommComment(Long commentID, CommCommentUpdateRequest commCommentUpdateRequest) {
    }

    public static void deletCommComment(Long commentId) {
    }
}
