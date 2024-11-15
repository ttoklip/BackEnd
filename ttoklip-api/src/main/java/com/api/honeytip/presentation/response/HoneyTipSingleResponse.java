package com.api.honeytip.presentation.response;

import com.api.global.util.TimeUtil;
import com.domain.comment.application.CommentResponse;
import com.domain.common.vo.Category;
import com.domain.common.vo.ImageResponse;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.honeytip.domain.HoneyTipComment;
import com.domain.honeytip.domain.HoneyTipImage;
import com.domain.honeytip.domain.HoneyTipUrl;
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

    @Schema(description = "꿀팁 작성자 프로필 사진 url", example = "작성자 프로필 사진 예시")
    private String writerProfileImageUrl;

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

    @Schema(description = "현재 사용자의 해당 게시글 스크랩 여부")
    private boolean scrapedByCurrentUser;

    public static HoneyTipSingleResponse of(final HoneyTip honeyTip,
                                            final List<HoneyTipComment> activeComments,
                                            final int likeCount,
                                            final int scrapCount,
                                            final boolean likedByCurrentUser,
                                            final boolean scrapedByCurrentUser) {
        String formattedCreatedDate = getFormattedCreatedDate(honeyTip);
        List<ImageResponse> imageResponses = getImageResponses(honeyTip);
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);
        List<UrlResponse> urlResponses = getUrlResponses(honeyTip);

        return builder()
                .honeyTipId(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .writer(honeyTip.getMember().getNickname())
                .writerProfileImageUrl(honeyTip.getMember().getProfile().getProfileImgUrl())
                .writtenTime(formattedCreatedDate)
                .category(honeyTip.getCategory())
                .likedByCurrentUser(likedByCurrentUser)
                .scrapedByCurrentUser(scrapedByCurrentUser)
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
        List<HoneyTipImage> honeyTipImageList = honeyTip.getHoneyTipImages();
        return honeyTipImageList
                .stream()
                .map(ImageResponse::honeyTipFrom)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<HoneyTipComment> comments) {
        return comments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    private static List<UrlResponse> getUrlResponses(final HoneyTip honeyTip) {
        List<HoneyTipUrl> honeyTipUrls = honeyTip.getHoneyTipUrls();
        return honeyTipUrls
                .stream()
                .map(UrlResponse::honeyTipUrlFrom)
                .toList();
    }
}
