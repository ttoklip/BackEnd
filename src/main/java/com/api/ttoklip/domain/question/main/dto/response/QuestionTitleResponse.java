package com.api.ttoklip.domain.question.main.dto.response;

import com.api.ttoklip.domain.question.main.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionTitleResponse {
    private Long questionId;
    private String title;
    private String content;
    private String writer;
    private int commentCount;
    private String writtenTime;
    private Category category;
}
