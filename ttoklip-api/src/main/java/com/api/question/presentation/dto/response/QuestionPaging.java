package com.api.question.presentation.dto.response;

import com.api.question.presentation.dto.response.vo.QuestionThumbnailResponse;
import java.util.List;
import lombok.Builder;
@Builder
public record QuestionPaging(List<QuestionThumbnailResponse> questions, Integer totalPage,
                             Long totalElements, Boolean isFirst, Boolean isLast) {
}
