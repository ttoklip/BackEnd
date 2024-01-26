package com.api.ttoklip.domain.town.cart.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CartCommentUpdateRequest {
    private String comment;
}
