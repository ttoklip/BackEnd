package com.api.ttoklip.domain.town.cart.comment.dto.request;

import lombok.Getter;

@Getter
public class CartCommentCreateRequest {

    private String comment;
    private Long parentCommentId;
}
