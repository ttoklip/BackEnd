package com.api.cart.presentation.dto.response;

import com.domain.cart.application.CartRecent3Response;
import java.util.List;
import lombok.Builder;
@Builder
public record CartPaging(List<CartRecent3Response> carts, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
