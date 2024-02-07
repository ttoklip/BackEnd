package com.api.ttoklip.domain.common.search.response;

import com.api.ttoklip.domain.common.Category;
import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HoneyTipSingleResponse {

    private Long id;
    private Category category;
    private String title;
    private String content;

//    private String writer;
//    private Long stars;
//    private Long likes;

    private int commentCount;

    public static HoneyTipSingleResponse from(final HoneyTip honeyTip){
        return HoneyTipSingleResponse.builder()
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
}
