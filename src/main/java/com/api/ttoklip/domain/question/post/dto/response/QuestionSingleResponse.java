package com.api.ttoklip.domain.question.post.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.question.comment.dto.response.QuestionCommentResponse;
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

    @Schema(description = "질문 작성자 프로필 사진 url", example = "작성자 프로필 사진 예시")
    private String writerProfileImageUrl;

    @Schema(description = "질문 작성 시간", example = "2024-01-11 10:00:00")
    private String writtenTime;

    @Schema(description = "질문 카테고리")
    private Category category;

    @Schema(description = "댓글 수")
    private int commentCount;

    @Schema(description = "질문에 포함된 이미지 URL 목록")
    private List<ImageResponse> imageUrls;

    @Schema(description = "질문에 대한 댓글 목록")
    private List<QuestionCommentResponse> questionCommentResponses;

    public static QuestionSingleResponse of(final Question question,
                                            final List<QuestionCommentResponse> commentResponses) {
        // 시간 포멧팅
        String formattedCreatedDate = getFormattedCreatedDate(question);

        // CommunityImage entity to Response
        List<ImageResponse> imageResponses = getImageResponses(question);

        // Comment entity to Response

        int commentCount = question.getQuestionComments().size();

        return QuestionSingleResponse.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .writer(question.getMember().getNickname())
                .writerProfileImageUrl(question.getMember().getProfile().getProfileImgUrl())
                .writtenTime(formattedCreatedDate)
                .category(question.getCategory()) // 한글 카테고리 이름으로 반환
                .commentCount(commentCount)
                .imageUrls(imageResponses)
                .questionCommentResponses(commentResponses)
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
                .map(ImageResponse::questionFrom)
                .toList();
    }

//    private static List<QuestionCommentResponse> getQuestionCommentResponses(final List<QuestionComment> questionComments, final Long currentUserId) {
//        return questionComments
//                .stream()
//                .map(questionComment -> QuestionCommentResponse.from(questionComment, currentUserId))
//                .collect(Collectors.toList());
//    }

}
