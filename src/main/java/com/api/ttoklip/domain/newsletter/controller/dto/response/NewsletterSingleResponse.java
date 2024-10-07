package com.api.ttoklip.domain.newsletter.controller.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.domain.NewsletterComment;
import com.api.ttoklip.global.util.TimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record NewsletterSingleResponse(
        @Schema(description = "뉴스레터 ID", example = "1")
        Long newsletterId,

        @Schema(description = "뉴스레터 제목", example = "전세 정책 뉴스")
        String title,

        @Schema(description = "뉴스레터 내용", example = "뉴스 레터 내용")
        String content,

        @Schema(description = "뉴스레터 작성자", example = "똑립뉴스")
        String writer,

        @Schema(description = "뉴스레터 작성 시간", example = "2024-01-01 10:00:00")
        String writtenTime,

        @Schema(description = "뉴스레터 카테고리")
        Category category,

        @Schema(description = "좋아요 개수", example = "3")
        int likeCount,

        @Schema(description = "스크랩 개수", example = "3")
        int scrapCount,

        @Schema(description = "댓글 개수", example = "14")
        int commentCount,

        @Schema(description = "뉴스레터에 포함된 이미지 URL 목록")
        List<NewsletterImageResponse> imageUrlList,

        @Schema(description = "뉴스레터에 포함된 URL 링크 목록")
        List<NewsletterUrlResponse> urlList,

        @Schema(description = "뉴스레터에 대한 댓글 목록")
        List<CommentResponse> commentResponses,

        @Schema(description = "현재 사용자의 해당 게시글 좋아요 여부")
        boolean likedByCurrentUser,

        @Schema(description = "현재 사용자의 해당 게시글 스크랩 여부")
        boolean scrapedByCurrentUser
) {
    public static NewsletterSingleResponse toDto(final Newsletter newsletter,
                                                 final List<NewsletterComment> activeComments,
                                                 final int likeCount,
                                                 final int scrapCount,
                                                 final boolean likedByCurrentUser,
                                                 final boolean scrapedByCurrentUser) {

        // 시간 포맷팅
        String formattedCreatedDate = getFormattedCreatedDate(newsletter);

        // Image entity to Response
        List<NewsletterImageResponse> newsletterImageRespons = getImageResponses(newsletter);

        // Comment entity to Response
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);

        // Url entity to Response
        List<NewsletterUrlResponse> urlResponses = getUrlResponse(newsletter);

        int commentCount = commentResponses.size();

        return new NewsletterSingleResponse(
                newsletter.getId(),
                newsletter.getTitle(),
                newsletter.getContent(),
                newsletter.getMember().getNickname(),
                formattedCreatedDate,
                newsletter.getCategory(),
                likeCount,
                scrapCount,
                commentCount,
                newsletterImageRespons,
                urlResponses,
                commentResponses,
                likedByCurrentUser,
                scrapedByCurrentUser
        );
    }

    private static String getFormattedCreatedDate(final Newsletter newsletter) {
        LocalDateTime createdDate = newsletter.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<NewsletterImageResponse> getImageResponses(final Newsletter newsletter) {
        return newsletter.getNewsletterImages()
                .stream()
                .map(NewsletterImageResponse::toDto)
                .toList();
    }

    private static List<NewsletterUrlResponse> getUrlResponse(final Newsletter newsletter) {
        return newsletter.getNewsletterUrlList()
                .stream()
                .map(NewsletterUrlResponse::toDto)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<NewsletterComment> newsletterComments) {
        return newsletterComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}
