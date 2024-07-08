package com.api.ttoklip.domain.town.cart.post.dto.response;

import com.api.ttoklip.domain.mypage.main.dto.response.UserCartSingleResponse;
import java.util.List;
import lombok.Builder;
@Builder
public record CartPaging(List<UserCartSingleResponse> carts, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
