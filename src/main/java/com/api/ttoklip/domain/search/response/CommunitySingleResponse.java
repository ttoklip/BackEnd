package com.api.ttoklip.domain.search.response;

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
    private String writer;
    private int commentCount;
    private int likeCount;
    private int scrapCount;

    public static CommunitySingleResponse from(final Community community) {
        return CommunitySingleResponse.builder()
                .id(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .commentCount(community.getCommunityComments().size())
                .writer(community.getMember().getNickname())
                .scrapCount(community.getCommunityScraps().size())
                .likeCount(community.getCommunityLikes().size())
                .build();
    }

    public static CommunitySingleResponse communityFrom(final Community community){
        return CommunitySingleResponse.builder()
                .id(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .commentCount(community.getCommunityComments().size())
                .writer(community.getMember().getNickname())
//                .street(community.getMember().getStreet())  todo 멤버 주소 추가되면 넣기
                .scrapCount(community.getCommunityScraps().size())
                .likeCount(community.getCommunityLikes().size())
                .build();
    }

}
