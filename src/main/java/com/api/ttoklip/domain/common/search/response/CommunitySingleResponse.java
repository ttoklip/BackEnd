package com.api.ttoklip.domain.common.search.response;

import com.api.ttoklip.domain.town.community.post.entity.Community;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunitySingleResponse {

    private Long id;
    private String title;
    private String content;

//    private String writer;
//    private Long stars;
//    private Long likes;

    private int commentCount;

    public static CommunitySingleResponse from(final Community community) {
        return CommunitySingleResponse.builder()
                .id(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .commentCount(community.getCommunityComments().size())
//                .writer(writer)
//                .stars(stars)
//                .likes(likes)
                .build();
    }

}
