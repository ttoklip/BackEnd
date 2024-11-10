package com.api.ttoklip.domain.mypage.dto.response;

import com.api.ttoklip.domain.question.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSingleResponse {
    private Long id;
    private Category category;
    private String title;
    private String content;
    private String writer;
    private String writerProfileImageUrl;
    private int commentCount;

    public static UserSingleResponse questionFrom(final Question question) {
        return UserSingleResponse.builder()
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
