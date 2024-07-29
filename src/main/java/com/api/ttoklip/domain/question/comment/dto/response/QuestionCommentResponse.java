package com.api.ttoklip.domain.question.comment.dto.response;

import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
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
public class QuestionCommentResponse {

    @Schema(description = "댓글 ID", example = "101")
    private Long commentId;

    @Schema(description = "댓글 내용", example = "댓글 내용 예시")
    private String commentContent;

    @Schema(description = "부모 댓글 ID (대댓글의 경우)", example = "1")
    private Long parentId;

    @Schema(description = "댓글 작성자", example = "댓글 작성자 예시")
    private String writer;

    @Schema(description = "댓글 작성자 프로필 이미지", example = "댓글 작성자 프로필 이미지 예시")
    private String writerProfileImageUrl;

    @Schema(description = "댓글 작성 시간", example = "2024-02-15 11:00:00")
    private String writtenTime;

    @Schema(description = "현재 사용자의 해당 댓글 좋아요 여부")
    private boolean likedByCurrentUser;

    public static QuestionCommentResponse from(final QuestionComment questionComment,
                                               final boolean likedByCurrentUser) {

        LocalDateTime createdDate = questionComment.getCreatedDate();
        String formatCreatedDate = TimeUtil.formatCreatedDate(createdDate);

        if (questionComment.getParent() == null) {
            return getQuestionCommentResponse(questionComment, null, formatCreatedDate, likedByCurrentUser);
        }

        return getQuestionCommentResponse(questionComment, questionComment.getParent().getId(), formatCreatedDate,
                likedByCurrentUser);
    }

    private static QuestionCommentResponse getQuestionCommentResponse(final QuestionComment questionComment,
                                                                      final Long parentCommentId,
                                                                      final String formatCreatedDate,
                                                                      final boolean likedByCurrentUser) {

        return QuestionCommentResponse.builder()
                .commentId(questionComment.getId())
                .commentContent(questionComment.getContent())
                .parentId(parentCommentId)
                .writer(questionComment.getMember().getNickname())
                .writerProfileImageUrl(questionComment.getMember().getProfile().getProfileImgUrl())
                .writtenTime(formatCreatedDate)
                .likedByCurrentUser(likedByCurrentUser)
                .build();
    }
}
