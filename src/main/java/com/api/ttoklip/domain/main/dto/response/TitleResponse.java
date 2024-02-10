package com.api.ttoklip.domain.main.dto.response;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.util.TimeUtil;
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
    private int likeCount;
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

    public static TitleResponse honeyTipFrom(final HoneyTip honeyTip) {
        LocalDateTime createdDate = honeyTip.getCreatedDate();
        String formattedCreatedDate = getFormattedCreatedDate(createdDate);
        int commentCount = honeyTip.getHoneyTipComments().size();
        int likeCount = honeyTip.getHoneyTipLikes().size();
        String writer = honeyTip.getMember().getName();

        return TitleResponse.builder()
                .honeyTipId(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .writer(writer)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .writtenTime(formattedCreatedDate)
                .build();
    }

    private static String getFormattedCreatedDate(final LocalDateTime localDateTime) {
        return TimeUtil.formatCreatedDate(localDateTime);
    }
}
