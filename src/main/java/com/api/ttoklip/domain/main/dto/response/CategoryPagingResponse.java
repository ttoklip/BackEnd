package com.api.ttoklip.domain.main.dto.response;

import com.api.ttoklip.domain.common.Category;
import java.util.List;
import lombok.Builder;

@Builder
public record CategoryPagingResponse(List<TitleResponse> data, Category category, Integer totalPage,
                                     Long totalElements, Boolean isFirst, Boolean isLast) {
}
