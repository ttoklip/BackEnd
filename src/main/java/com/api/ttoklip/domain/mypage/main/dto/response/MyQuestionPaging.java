package com.api.ttoklip.domain.mypage.main.dto.response;

import com.api.ttoklip.domain.search.response.CommunitySingleResponse;
import lombok.Builder;

import java.util.List;
@Builder
public record MyQuestionPaging(List<UserSingleResponse> questions, Integer totalPage,
                               Long totalElements, Boolean isFirst, Boolean isLast) {
}
