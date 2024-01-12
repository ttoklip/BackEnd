package com.api.ttoklip.domain.question.post.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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
}
