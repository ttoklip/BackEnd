package com.api.ttoklip.domain.town.community.comment.service;

import com.api.ttoklip.domain.town.community.comment.dto.request.CommunityCommentCreateRequest;
import com.api.ttoklip.domain.town.community.comment.dto.request.CommunityCommentUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class CommunityCommentService {
    public Long createCommComment(final Long commId, final CommunityCommentCreateRequest request) {
        return null;
    }

    public static void updateCommComment(final Long commentID, final CommunityCommentUpdateRequest commCommentUpdateRequest) {
    }

    public static void deletCommComment(final Long commentId) {
    }
}
