package com.api.ttoklip.domain.town.community.post.dto.response;

import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.dto.response.ImageResponse;
import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunitySingleResponse {

    @Schema(description = " ID", example = "1")
    private Long communityId;

    @Schema(description = "소통해요 제목", example = "소통해요 제목 예시")
    private String title;

    @Schema(description = "소통해요 내용", example = "소통해요 내용 예시")
    private String content;

    @Schema(description = "소통해요 작성자", example = "작성자 예시")
    private String writer;

    @Schema(description = "소통해요 작성 시간", example = "2024-01-15 10:00:00")
    private String writtenTime;

    @Schema(description = "소통해요에 포함된 이미지 URL 목록")
    private List<ImageResponse> imageUrls;

    @Schema(description = "소통해요에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;

    public static CommunitySingleResponse from(final Community community) {

        // 시간 포멧팅
        String formattedCreatedDate = getFormattedCreatedDate(community);

        // Image entity to Response
        List<ImageResponse> imageResponses = getImageResponses(community);

        // Comment entity to Response
        List<CommentResponse> commentResponses = getCommentResponses(community);

        return CommunitySingleResponse.builder()
                .communityId(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
//                .writer(question.getMember().getName) ToDo Member 엔티티 연결 후 수정
                .writtenTime(formattedCreatedDate)
                .imageUrls(imageResponses)
                .commentResponses(commentResponses)
                .build();
    }

    private static String getFormattedCreatedDate(final Community community) {
        LocalDateTime createdDate = community.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<ImageResponse> getImageResponses(final Community community) {
        List<CommunityImage> communityImages = community.getCommunityImages();
        return communityImages
                .stream()
                .map(ImageResponse::from)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final Community community) {
        List<CommunityComment> communityComments = community.getCommunityComments();
        return communityComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
