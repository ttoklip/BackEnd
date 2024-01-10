package com.api.ttoklip.domain.question.dto.response;

public record QuestionSearchResponse(QuestionResponse searchData,  Integer totalPage,
                                     Long totalElements, Boolean isFirst, Boolean isLast) {
}