package com.api.ttoklip.domain.mypage.main.dto.response;

import java.util.List;
import lombok.Builder;
@Builder
public record QuestionPaging(List<UserSingleResponse> questions, Integer totalPage,
                             Long totalElements, Boolean isFirst, Boolean isLast) {
}
