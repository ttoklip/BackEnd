package com.api.ttoklip.domain.town.cart.comment.service;

import com.api.ttoklip.domain.town.cart.comment.dto.request.CartCommentCreateRequest;
import com.api.ttoklip.domain.town.cart.comment.dto.request.CartCommentUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public class CartCommentService {
    public Long createCartComment(Long cartId, CartCommentCreateRequest request) {
        return null;
    }

    public static void updateCartComment(Long commentId, CartCommentUpdateRequest cartCommentUpdateRequest) {
    }

    public static void deletCartComment(Long commentId) {
    }
}
