package com.api.ttoklip.domain.question.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponse {
    private Long questionId;
    private String title;
    private String content;
    private String writer;
    private int commentCount;
    private String writtenTime;
}
