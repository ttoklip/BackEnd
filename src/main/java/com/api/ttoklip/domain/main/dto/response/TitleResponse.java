package com.api.ttoklip.domain.main.dto.response;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.util.TimeUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TitleResponse {
    private Long honeyTipId;
    private String title;
    private String content;
    private String writer;
    private int commentCount;
    private String writtenTime;

    public static TitleResponse questionOf(final Question question) {
        LocalDateTime createdDate = question.getCreatedDate();
        String formattedCreatedDate = getFormattedCreatedDate(createdDate);

        return TitleResponse.builder()
                .honeyTipId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
//                .writer(quesion.member.getName())
                .commentCount(question.getQuestionComments().size())
                .writtenTime(formattedCreatedDate)
                .build();
    }

    public static TitleResponse honeyTipOf(final HoneyTip honeyTip) {
        LocalDateTime createdDate = honeyTip.getCreatedDate();
        String formattedCreatedDate = getFormattedCreatedDate(createdDate);

        return TitleResponse.builder()
                .honeyTipId(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
//                .writer(honeyTip.member.getName())
                .commentCount(honeyTip.getHoneyTipComments().size())
                .writtenTime(formattedCreatedDate)
                .build();
    }

    private static String getFormattedCreatedDate(final LocalDateTime localDateTime) {
        return TimeUtil.formatCreatedDate(localDateTime);
    }
}
