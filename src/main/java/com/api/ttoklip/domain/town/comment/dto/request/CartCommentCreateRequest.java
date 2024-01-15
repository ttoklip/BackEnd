package com.api.ttoklip.domain.town.comment.dto.request;

import lombok.Getter;

@Getter
public class CartCommentCreateRequest {

    private String comment;
    private Long parentCommentId;
}
