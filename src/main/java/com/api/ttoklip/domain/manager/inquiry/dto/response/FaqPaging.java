package com.api.ttoklip.domain.manager.inquiry.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record FaqPaging(List<FaqSingleResponse> faqs, Integer totalPage,
                        Long totalElements, Boolean isFirst, Boolean isLast) {
}
