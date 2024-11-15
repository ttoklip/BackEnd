package com.domain.common.vo;

import com.domain.honeytip.domain.HoneyTip;
import com.domain.question.domain.Question;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;

@Builder
public record TitleResponse(Long id, String title, String content, String writer, String writerProfileImageUrl,
                            int likeCount, int commentCount, int scrapCount, String writtenTime) {
    private static final String DATE_NONE = "날짜 없음";

    public static TitleResponse questionOf(final Question question) {
        LocalDateTime createdDate = question.getCreatedDate();
        String formattedCreatedDate = getFormattedCreatedDate(createdDate);
        int commentCount = question.getQuestionComments().size();
        String writer = question.getMember().getNickname();

        return builder()
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

        return builder()
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
        if (localDateTime == null) {
            return DATE_NONE;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
        return localDateTime.format(formatter);
    }
}
