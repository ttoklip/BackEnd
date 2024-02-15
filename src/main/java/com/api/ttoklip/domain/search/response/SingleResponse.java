package com.api.ttoklip.domain.search.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import com.api.ttoklip.domain.newsletter.post.domain.Newsletter;
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

//    private String writer;
//    private Long stars;
//    private Long likes;

    private int commentCount;

    public static SingleResponse honeyTipFrom(final HoneyTip honeyTip){
        return SingleResponse.builder()
                .id(honeyTip.getId())
                .title(honeyTip.getTitle())
                .content(honeyTip.getContent())
                .category(honeyTip.getCategory())
                .commentCount(honeyTip.getHoneyTipComments().size())
//                .writer(writer)
//                .stars(stars)
//                .likes(likes)
                .build();
    }

    public static SingleResponse newsletterFrom(final Newsletter newsletter){
        return SingleResponse.builder()
                .id(newsletter.getId())
                .title(newsletter.getTitle())
                .content(newsletter.getContent())
                .category(newsletter.getCategory())
                .commentCount(newsletter.getNewsletterComments().size())
//                .writer(writer)
//                .stars(stars)
//                .likes(likes)
                .build();
    }
}
