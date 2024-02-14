package com.api.ttoklip.domain.newsletter.post.dto.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.common.comment.dto.response.CommentResponse;
import com.api.ttoklip.domain.newsletter.comment.domain.NewsletterComment;
import com.api.ttoklip.domain.newsletter.image.domain.NewsletterImage;
import com.api.ttoklip.domain.newsletter.image.dto.response.ImageRes;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.url.domain.NewsletterUrl;
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
public class NewsletterSingleResponse {

    @Schema(description = "뉴스레터 ID", example = "1")
    private Long newsletterId;

    @Schema(description = "뉴스레터 제목", example = "전세 정책 뉴스")
    private String title;

    @Schema(description = "뉴스레터 내용", example = "뉴스 레터 내용")
    private String content;

    @Schema(description = "뉴스레터 작성자", example = "똑립뉴스")
    private String writer;

    @Schema(description = "뉴스레터 작성 시간", example = "2024-01-01 10:00:00")
    private String writtenTime;

    @Schema(description = "뉴스레터 카테고리")
    private Category category;

    @Schema(description = "스크랩 개수", example = "3")
    private int scrapCount;

    @Schema(description = "뉴스레터에 포함된 이미지 URL 목록")
    private List<ImageRes> imageUrlList;

    @Schema(description = "뉴스레터에 포함된 URL 링크 목록")
    private List<UrlRes> urlList;

    @Schema(description = "뉴스레터에 대한 댓글 목록")
    private List<CommentResponse> commentResponses;



    public static NewsletterSingleResponse toDto(final Newsletter newsletter,
                                                 final List<NewsletterComment> activeComments,
                                                 final int scrapCount) {

        // 시간 포멧팅
        String formattedCreatedDate = getFormattedCreatedDate(newsletter);

        // Image entity to Response
        List<ImageRes> imageResponses = getImageResponses(newsletter);

        // Comment entity to Response
        List<CommentResponse> commentResponses = getCommentResponses(activeComments);

        // Url entity to Response
        List<UrlRes> urlResponses = getUrlResponse(newsletter);

        return NewsletterSingleResponse.builder()
                .newsletterId(newsletter.getId())
                .title(newsletter.getTitle())
                .content(newsletter.getContent())
                .writer(newsletter.getMember().getOriginName())
                .writtenTime(formattedCreatedDate)
                .category(newsletter.getCategory())
                .imageUrlList(imageResponses)
                .urlList(urlResponses)
                .commentResponses(commentResponses)
                .scrapCount(scrapCount)
                .build();
    }

    private static String getFormattedCreatedDate(final Newsletter newsletter) {
        LocalDateTime createdDate = newsletter.getCreatedDate();
        return TimeUtil.formatCreatedDate(createdDate);
    }

    private static List<ImageRes> getImageResponses(final Newsletter newsletter) {
        List<NewsletterImage> newsletterImages = newsletter.getNewsletterImageList();
        return newsletterImages
                .stream()
                .map(ImageRes::toDto)
                .toList();
    }

    private static List<UrlRes> getUrlResponse(final Newsletter newsletter) {
        List<NewsletterUrl> newsletterUrls = newsletter.getNewsletterUrlList();
        return newsletterUrls
                .stream()
                .map(UrlRes::toDto)
                .toList();
    }

    private static List<CommentResponse> getCommentResponses(final List<NewsletterComment> newsletterComments) {
        return newsletterComments
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
}