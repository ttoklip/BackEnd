package com.api.cart.presentation.dto.response;

import com.domain.cart.application.CartThumbnailResponse;
import java.util.List;
import lombok.Builder;
@Builder
public record CartPaging(List<CartThumbnailResponse> carts, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
