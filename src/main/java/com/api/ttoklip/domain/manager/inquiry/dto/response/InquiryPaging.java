package com.api.ttoklip.domain.manager.inquiry.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record InquiryPaging(List<InquirySingleResponse> inquiries, Integer totalPage,
                            Long totalElements, Boolean isFirst, Boolean isLast) {
}
