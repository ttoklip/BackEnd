package com.api.ttoklip.domain.town.cart.post.service;

import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
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

    public CartSingleResponse getCart(final Long cartId) {
        return null;
    }

    public CartSingleResponse createCartPost(final CartCreateRequest request) {
        return null;
    }

    public void updateCartPost(final Long cartId, final CartUpdateRequest request) {
        return;
    }

    public List<CartSummaryResponse> getAllCartsSummary() { return null;
    }
}
