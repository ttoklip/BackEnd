package com.api.ttoklip.domain.town.community.post.dto.response;

import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.dto.response.CommunityImageResponse;
import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.url.dto.response.UrlResponse;
import com.api.ttoklip.domain.town.community.url.entity.CommunityUrl;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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

    @Schema(description = "현재 사용자가 좋아요를 눌렀는지 여부", example = "true")
    private boolean liked;

    @Schema(description = "소통해요에 포함된 이미지 URL 목록")
    private List<CommunityImageResponse> imageUrls;

    @Schema(description = "소통해요에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;

    public static CommunitySingleResponse of(final Community community,
                                             final List<CommunityComment> activeComments) {

        // 시간 포멧팅
        String formattedCreatedDate = getFormattedCreatedDate(community);

        // CartImage entity to Response
        List<CommunityImageResponse> communityImageResponses = getImageResponses(community);

        // Comment entity to Response
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);

        return CommunitySingleResponse.builder()
                .communityId(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
//                .writer(community.getMember().getName) ToDo Member 엔티티 연결 후 수정
                .writtenTime(formattedCreatedDate)
                .imageUrls(communityImageResponses)
                .commentResponses(commentResponses)
//                .liked(liked)   ToDo Member 엔티티 연결 후 수정
                .build();
    }

    private static String getFormattedCreatedDate(final Community community) {
        LocalDateTime createdDate = community.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<CommunityImageResponse> getImageResponses(final Community community) {
        List<CommunityImage> communityImages = community.getCommunityImages();
        return communityImages
                .stream()
                .map(CommunityImageResponse::from)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<CommunityComment> communityComments) {
        return communityComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
