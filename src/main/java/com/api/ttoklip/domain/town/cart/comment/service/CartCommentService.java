package com.api.ttoklip.domain.town.cart.comment.service;

import com.api.ttoklip.domain.town.cart.comment.dto.request.CartCommentCreateRequest;
import com.api.ttoklip.domain.town.cart.comment.dto.request.CartCommentUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class CartCommentService {
    public Long createCartComment(final Long cartId, final CartCommentCreateRequest request) {
        return null;
    }

    public static void updateCartComment(final Long commentId, final CartCommentUpdateRequest cartCommentUpdateRequest) {
    }

    public static void deletCartComment(final Long commentId) {
    }
}
