package com.api.ttoklip.domain.question.post.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.question.image.dto.response.ImageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionWithCommentResponse {

    @Schema(description = "질문 ID", example = "1")
    private Long questionId;

    @Schema(description = "질문 제목", example = "질문 제목 예시")
    private String title;

    @Schema(description = "질문 내용", example = "질문 내용 예시")
    private String content;

    @Schema(description = "질문 작성자", example = "작성자 예시")
    private String writer;

    @Schema(description = "질문 작성 시간", example = "2024-01-11 10:00:00")
    private String writtenTime;

    @Schema(description = "질문 카테고리")
    private Category category;

    @Schema(description = "질문에 포함된 이미지 URL 목록")
    private List<ImageResponse> imageUrls;

    @Schema(description = "질문에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;
}
