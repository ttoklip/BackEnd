package com.api.ttoklip.domain.honeytip.post.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.honeytip.comment.domain.HoneyTipComment;
import com.api.ttoklip.domain.honeytip.image.domain.HoneyTipImage;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.url.domain.HoneyTipUrl;
import com.api.ttoklip.domain.honeytip.url.dto.response.UrlResponse;
import com.api.ttoklip.domain.question.image.dto.response.ImageResponse;
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
public class HoneyTipSingleResponse {

    @Schema(description = "꿀팁 ID", example = "1")
    private Long honeyTipId;

    @Schema(description = "꿀팁 제목", example = "질문 제목 예시")
    private String title;

    @Schema(description = "꿀팁 내용", example = "질문 내용 예시")
    private String content;

    @Schema(description = "꿀팁 작성자", example = "작성자 예시")
    private String writer;

    @Schema(description = "질문 작성 시간", example = "2024-01-11 10:00:00")
    private String writtenTime;

    @Schema(description = "꿀팁 카테고리")
    private Category category;

    @Schema(description = "좋아요 개수", example = "2")
    private int likeCount;

    @Schema(description = "스크랩 개수", example = "3")
    private int scrapCount;

    @Schema(description = "댓글 개수", example = "5")
    private int commentCount;

    @Schema(description = "꿀팁에 포함된 이미지 URL 목록")
    private List<ImageResponse> imageUrls;

    @Schema(description = "꿀팁에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;

    @Schema(description = "꿀팁 채팅 url 목록")
    private List<UrlResponse> urlResponses;

    @Schema(description = "현재 사용자의 해당 게시글 좋아요 여부")
    private boolean likedByCurrentUser;

    public static HoneyTipSingleResponse of(final HoneyTip honeyTip,
                                            final List<HoneyTipComment> activeComments,
                                            final int likeCount,
                                            final int scrapCount,
                                            final boolean likedByCurrentUser) {
        String formattedCreatedDate = getFormattedCreatedDate(honeyTip);
        List<ImageResponse> imageResponses = getImageResponses(honeyTip);
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);
        List<UrlResponse> urlResponses = getUrlResponses(honeyTip);

        return HoneyTipSingleResponse.builder()
                .honeyTipId(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .writer(honeyTip.getMember().getNickname())
                .writtenTime(formattedCreatedDate)
                .category(honeyTip.getCategory())
                .likedByCurrentUser(likedByCurrentUser)
                .likeCount(likeCount)
                .scrapCount(scrapCount)
                .commentCount(commentResponses.size())
                .imageUrls(imageResponses)
                .commentResponses(commentResponses)
                .urlResponses(urlResponses)
                .build();
    }

    private static String getFormattedCreatedDate(final HoneyTip honeyTip) {
        LocalDateTime createdDate = honeyTip.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<ImageResponse> getImageResponses(final HoneyTip honeyTip) {
        List<HoneyTipImage> honeyTipImageList = honeyTip.getHoneyTipImageList();
        return honeyTipImageList
                .stream()
                .map(ImageResponse::honeyTipFrom)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<HoneyTipComment> questionComments) {
        return questionComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    private static List<UrlResponse> getUrlResponses(final HoneyTip honeyTip) {
        List<HoneyTipUrl> honeyTipUrls = honeyTip.getHoneyTipUrlList();
        return honeyTipUrls
                .stream()
                .map(UrlResponse::honeyTipUrlFrom)
                .toList();
    }
}
