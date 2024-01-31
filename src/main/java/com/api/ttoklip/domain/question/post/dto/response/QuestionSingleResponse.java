package com.api.ttoklip.domain.question.post.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.question.comment.domain.QuestionComment;
import com.api.ttoklip.domain.question.image.domain.QuestionImage;
import com.api.ttoklip.domain.question.image.dto.response.ImageResponse;
import com.api.ttoklip.domain.question.post.domain.Question;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionSingleResponse {

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

    public static QuestionSingleResponse of(final Question question, final List<QuestionComment> activeComments) {

        // 시간 포멧팅
        String formattedCreatedDate = getFormattedCreatedDate(question);

        // Image entity to Response
        List<ImageResponse> imageResponses = getImageResponses(question);

        // Comment entity to Response
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);

        return QuestionSingleResponse.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
//                .writer(question.getMember().getName) ToDo Member 엔티티 연결 후 수정
                .writtenTime(formattedCreatedDate)
                .category(question.getCategory()) // 한글 카테고리 이름으로 반환
                .imageUrls(imageResponses)
                .commentResponses(commentResponses)
                .build();
    }

    private static String getFormattedCreatedDate(final Question question) {
        LocalDateTime createdDate = question.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<ImageResponse> getImageResponses(final Question question) {
        List<QuestionImage> questionImages = question.getQuestionImages();
        return questionImages
                .stream()
                .map(ImageResponse::from)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<QuestionComment> questionComments) {
        return questionComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
