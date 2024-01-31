package com.api.ttoklip.domain.common.comment.dto.response;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponse {

    @Schema(description = "댓글 ID", example = "101")
    private Long commentId;

    @Schema(description = "댓글 내용", example = "댓글 내용 예시")
    private String commentContent;

    @Schema(description = "부모 댓글 ID (대댓글의 경우)", example = "1")
    private Long parentId;

    @Schema(description = "댓글 작성자", example = "댓글 작성자 예시")
    private String writer;

    @Schema(description = "댓글 작성 시간", example = "2024-01-11 11:00:00")
    private String writtenTime;


    public static CommentResponse from(final Comment questionComment) {
        LocalDateTime createdDate = questionComment.getCreatedDate();
        String formatCreatedDate = TimeUtil.formatCreatedDate(createdDate);

        if (questionComment.getParent() == null) {
            return getCommentResponse(questionComment, null, formatCreatedDate);
        }

        return getCommentResponse(questionComment, questionComment.getParent().getId(), formatCreatedDate);
    }

    private static CommentResponse getCommentResponse(final Comment questionComment, final Long parentCommentId,
                                                      final String formatCreatedDate) {
        return CommentResponse.builder()
                .commentId(questionComment.getId())
                .commentContent(questionComment.getContent())
                .parentId(parentCommentId) // 부모 댓글이 있는 경우
//            .writer(questionComment.getMember().getName()) // ToDo Member Entity 생성 후 수정
                .writtenTime(formatCreatedDate)
                .build();
    }
}
