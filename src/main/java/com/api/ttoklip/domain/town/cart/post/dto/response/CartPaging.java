package com.api.ttoklip.domain.town.cart.post.dto.response;

import com.api.ttoklip.domain.mypage.main.dto.response.UserCartSingleResponse;
import lombok.Builder;

import java.util.List;
@Builder
public record CartPaging(List<UserCartSingleResponse> carts, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
