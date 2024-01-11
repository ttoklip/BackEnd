package com.api.ttoklip.domain.town.dto.response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import java.util.List;

@Builder
public record CartListResponse(List<CartResponse> cartResponses, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
