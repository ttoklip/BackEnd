package com.api.ttoklip.domain.town.cart.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record CartListResponse(List<CartResponse> cartResponses, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
