package com.api.ttoklip.domain.main.dto.response;

import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.question.domain.Question;
import com.api.ttoklip.global.util.TimeUtil;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TitleResponse {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String writerProfileImageUrl;
    private int likeCount;
    private int commentCount;
    private int scrapCount;
    private String writtenTime;

    public static TitleResponse questionOf(final Question question) {
        LocalDateTime createdDate = question.getCreatedDate();
        String formattedCreatedDate = getFormattedCreatedDate(createdDate);
        int commentCount = question.getQuestionComments().size();
        String writer = question.getMember().getNickname();

        return TitleResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .writer(writer)
                .writerProfileImageUrl(question.getMember().getProfile().getProfileImgUrl())
                .commentCount(commentCount)
                .writtenTime(formattedCreatedDate)
                .build();
    }

    public static TitleResponse honeyTipFrom(final HoneyTip honeyTip) {
        LocalDateTime createdDate = honeyTip.getCreatedDate();
        String formattedCreatedDate = getFormattedCreatedDate(createdDate);
        int commentCount = honeyTip.getHoneyTipComments().size();
        int likeCount = honeyTip.getHoneyTipLikes().size();
        int scrapCount = honeyTip.getHoneyTipScraps().size();
        String writer = honeyTip.getMember().getNickname();

        return TitleResponse.builder()
                .id(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .writer(writer)
                .writerProfileImageUrl(honeyTip.getMember().getProfile().getProfileImgUrl())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .scrapCount(scrapCount)
                .writtenTime(formattedCreatedDate)
                .build();
    }

    private static String getFormattedCreatedDate(final LocalDateTime localDateTime) {
        return TimeUtil.formatCreatedDate(localDateTime);
    }
}
