package com.api.ttoklip.domain.town.community.comment.service;

import com.api.ttoklip.domain.town.community.comment.dto.request.CommunityCommentCreateRequest;
import com.api.ttoklip.domain.town.community.comment.dto.request.CommunityCommentUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class CommunityCommentService {
    public Long createCommComment(Long commId, CommunityCommentCreateRequest request) {
        return null;
    }

    public static void updateCommComment(Long commentID, CommunityCommentUpdateRequest commCommentUpdateRequest) {
    }

    public static void deletCommComment(Long commentId) {
    }
}
