package com.api.ttoklip.domain.town.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommSearchCondition {
    @Schema(description = "검색할 작성자 이름", example = "작성자1")
    private String writer;

    @Schema(description = "검색할 내용", example = "내용입니다.")
    private String content;

    @Schema(description = "검색할 제목", example = "제목입니다.")
    private String title;
}
