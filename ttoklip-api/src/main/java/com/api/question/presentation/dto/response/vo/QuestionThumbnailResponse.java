package com.api.question.presentation.dto.response.vo;

import com.domain.common.vo.Category;
import com.domain.question.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionThumbnailResponse {
    private Long id;
    private Category category;
    private String title;
    private String content;
    private String writer;
    private String writerProfileImageUrl;
    private int commentCount;

    public static QuestionThumbnailResponse from(final Question question) {
        return builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategory())
                .commentCount(question.getQuestionComments().size())
                .writer(question.getMember().getNickname())
                .writerProfileImageUrl(question.getMember().getProfile().getProfileImgUrl())
                .build();
    }
}
