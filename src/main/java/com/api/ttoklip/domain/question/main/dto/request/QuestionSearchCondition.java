package com.api.ttoklip.domain.question.main.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionSearchCondition {
    @Schema(description = "검색할 작성자 이름", example = "작성자1")
    private String writer;

    @Schema(description = "검색할 내용", example = "내용 예시")
    private String content;

    @Schema(description = "검색할 제목", example = "제목 예시")
    private String title;
}
