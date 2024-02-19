package com.api.ttoklip.domain.mypage.main.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record QuestionPaging(List<UserSingleResponse> questions, Integer totalPage,
                             Long totalElements, Boolean isFirst, Boolean isLast) {
}
