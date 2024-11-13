package com.api.search.presentation.response;

import com.domain.common.vo.Category;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.newsletter.domain.Newsletter;
import lombok.Builder;

@Builder
public record CommonThumbnailResponse(
        Long id, Category category, String title, String content, String writer,
        String writerProfileImageUrl, int likeCount, int scrapCount, int commentCount
) {

    public static CommonThumbnailResponse honeyTipFrom(final HoneyTip honeyTip) {
        return CommonThumbnailResponse.builder()
                .id(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .category(honeyTip.getCategory())
                .commentCount(honeyTip.getHoneyTipComments().size())
                .writer(honeyTip.getMember().getNickname())
                .writerProfileImageUrl(honeyTip.getMember().getProfile().getProfileImgUrl())
                .scrapCount(honeyTip.getHoneyTipScraps().size())
                .likeCount(honeyTip.getHoneyTipLikes().size())
                .build();
    }

    public static CommonThumbnailResponse newsletterFrom(final Newsletter newsletter) {
        return CommonThumbnailResponse.builder()
                .id(newsletter.getId())
                .title(newsletter.getTitle())
                .content(newsletter.getContent())
                .category(newsletter.getCategory())
                .commentCount(newsletter.getNewsletterComments().size())
                .writer(newsletter.getMember().getNickname())
                .scrapCount(newsletter.getNewsletterScraps().size())
                .likeCount(newsletter.getNewsletterLikes().size())
                .build();
    }

}
