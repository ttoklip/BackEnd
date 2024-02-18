package com.api.ttoklip.domain.newsletter.main.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record NewsCategoryPagingResponse(List<CategoryResponse> categoryResponses, Integer totalPage,
                                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
