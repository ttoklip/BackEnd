package com.domain.common.comment.application;

import com.domain.common.comment.domain.Comment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    private Long commentId;

    private String commentContent;

    private Long parentId;

    private String writer;

    private String writerProfileImageUrl;

    private String writtenTime;

    public static CommentResponse from(final Comment comment) {
        LocalDateTime createdDate = comment.getCreatedDate();
        String formatCreatedDate = formatCreatedDate(createdDate);

        if (comment.getParent() == null) {
            return getCommentResponse(comment, null, formatCreatedDate);
        }

        return getCommentResponse(comment, comment.getParent().getId(), formatCreatedDate);
    }

    private static CommentResponse getCommentResponse(final Comment comment, final Long parentCommentId,
                                                      final String formatCreatedDate) {
        String nickname = comment.getMember().getNickname();
        return CommentResponse.builder()
                .commentId(comment.getId())
                .commentContent(comment.getContent())
                .parentId(parentCommentId)
                .writer(nickname)
                .writerProfileImageUrl(comment.getMember().getProfile().getProfileImgUrl())
                .writtenTime(formatCreatedDate)
                .build();
    }

    private static final String DATE_NONE = "날짜 없음";

    private static String formatCreatedDate(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return DATE_NONE;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
        return localDateTime.format(formatter);
    }
}
