package com.domain.common.comment.dto.response;

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

    public static CommentResponse from(final Comment questionComment) {
        LocalDateTime createdDate = questionComment.getCreatedDate();
        String formatCreatedDate = formatCreatedDate(createdDate);

        if (questionComment.getParent() == null) {
            return getCommentResponse(questionComment, null, formatCreatedDate);
        }

        return getCommentResponse(questionComment, questionComment.getParent().getId(), formatCreatedDate);
    }

    private static CommentResponse getCommentResponse(final Comment questionComment, final Long parentCommentId,
                                                      final String formatCreatedDate) {
        String nickname = questionComment.getMember().getNickname();
        return CommentResponse.builder()
                .commentId(questionComment.getId())
                .commentContent(questionComment.getContent())
                .parentId(parentCommentId)
                .writer(nickname)
                .writerProfileImageUrl(questionComment.getMember().getProfile().getProfileImgUrl())
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
