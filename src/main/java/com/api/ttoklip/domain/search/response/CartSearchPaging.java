package com.api.ttoklip.domain.search.response;

import com.api.ttoklip.domain.mypage.main.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CartSearchPaging(List<UserCartSingleResponse> carts, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
