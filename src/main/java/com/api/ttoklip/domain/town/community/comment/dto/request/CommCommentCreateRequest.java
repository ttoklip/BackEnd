package com.api.ttoklip.domain.town.community.comment.dto.request;

import lombok.Getter;

@Getter
public class CommCommentCreateRequest {

    private String comment;
    private Long parentCommentId;
}
