package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.town.cart.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.dto.request.CartSearchCondition;
import com.api.ttoklip.domain.town.cart.dto.request.CartUpdateRequest;
import com.api.ttoklip.domain.town.cart.dto.response.CartListResponse;
import com.api.ttoklip.domain.town.cart.dto.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class CartService {

    // 더보기 페이지 조회
    public static CartListResponse searchCartPaging(CartSearchCondition condition, Pageable pageable) {
        return null;
    }

    public CartResponse getCart(Long cartId) {
        return null;
    }

    public CartResponse createCartPost(CartCreateRequest request) {
        return null;
    }

    public void updateCartPost(Long cartId, CartUpdateRequest request) {
        return;
    }
}
