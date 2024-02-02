package com.api.ttoklip.domain.town.cart.post.service;

import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartSearchCondition;
import com.api.ttoklip.domain.town.cart.post.dto.request.CartUpdateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartListResponse;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartResponse;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartPostService {

    public static CartListResponse searchCartPaging(final CartSearchCondition condition, final Pageable pageable) {
        return null;
    }

    public CartResponse getCart(final Long cartId) {
        return null;
    }

    public CartResponse createCartPost(final CartCreateRequest request) {
        return null;
    }

    public void updateCartPost(final Long cartId, final CartUpdateRequest request) {
        return;
    }

    public List<CartSummaryResponse> getAllCartsSummary() { return null;
    }
}
