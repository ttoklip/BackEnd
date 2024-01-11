package com.api.ttoklip.domain.town.service;

import com.api.ttoklip.domain.town.dto.request.CartSearchCondition;
import com.api.ttoklip.domain.town.dto.response.CartListResponse;
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
}
