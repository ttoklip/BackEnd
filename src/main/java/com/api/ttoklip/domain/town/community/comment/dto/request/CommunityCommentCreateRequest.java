package com.api.ttoklip.domain.town.community.comment.dto.request;

import lombok.Getter;

@Getter
public class CommunityCommentCreateRequest {

    private String comment;
    private Long parentCommentId;
}
