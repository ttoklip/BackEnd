package com.api.ttoklip.domain.search.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SingleResponse {

    private Long id;
    private Category category;
    private String title;
    private String content;
    private String writer;
    private String writerProfileImageUrl;
    private int likeCount;
    private int scrapCount;
    private int commentCount;

    public static SingleResponse honeyTipFrom(final HoneyTip honeyTip) {
        return SingleResponse.builder()
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

    public static SingleResponse newsletterFrom(final Newsletter newsletter) {
        return SingleResponse.builder()
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
