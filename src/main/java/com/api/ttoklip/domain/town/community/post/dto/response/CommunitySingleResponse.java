package com.api.ttoklip.domain.town.community.post.dto.response;

import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.town.community.comment.CommunityComment;
import com.api.ttoklip.domain.town.community.image.dto.response.CommunityImageResponse;
import com.api.ttoklip.domain.town.community.image.entity.CommunityImage;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @Schema(description = "좋아요 개수", example = "5")
    private int likeCount;

    @Schema(description = "스크랩 개수", example = "3")
    private int scrapCount;

    @Schema(description = "댓글 개수", example = "11")
    private int commentCount;

    @Schema(description = "소통해요에 포함된 이미지 URL 목록")
    private List<CommunityImageResponse> imageUrls;

    @Schema(description = "소통해요에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;

    @Schema(description = "현재 사용자의 해당 게시글 좋아요 여부")
    private boolean likedByCurrentUser;

    @Schema(description = "현재 사용자의 해당 게시글 스크랩 여부")
    private boolean scrapedByCurrentUser;

    public static CommunitySingleResponse of(final Community community,
                                             final List<CommunityComment> activeComments,
                                             final int likeCount,
                                             final int scrapCount,
                                             final boolean likedByCurrentUser,
                                             final boolean scrapedByCurrentUser) {

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
                .writer(community.getMember().getNickname())
                .writtenTime(formattedCreatedDate)
                .likedByCurrentUser(likedByCurrentUser)
                .scrapedByCurrentUser(scrapedByCurrentUser)
                .imageUrls(communityImageResponses)
                .commentResponses(commentResponses)
                .likeCount(likeCount)
                .scrapCount(scrapCount)
                .commentCount(commentResponses.size())
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
