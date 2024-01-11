package com.api.ttoklip.domain.question.main.dto.response;

import java.util.List;

public record QuestionSearchResponse(List<QuestionResponse> searchData, Integer totalPage,
                                     Long totalElements, Boolean isFirst, Boolean isLast) {
}
