package com.api.ttoklip.domain.mypage.term.dto.response;

;

import lombok.Builder;

import java.util.List;

@Builder
public record TermPaging(List<TermSingleResponse> terms, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
