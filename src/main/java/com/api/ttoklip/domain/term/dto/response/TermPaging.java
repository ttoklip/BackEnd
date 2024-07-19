package com.api.ttoklip.domain.term.dto.response;

import java.util.List;
import lombok.Builder;

;

@Builder
public record TermPaging(List<TermSingleResponse> terms, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
