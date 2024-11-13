package com.api.town.application;

import com.domain.community.domain.Community;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TownCommunityResponse {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String writerProfileImageUrl;
    private int commentCount;
    private int likeCount;
    private int scrapCount;

    public static TownCommunityResponse from(final Community community) {
        return builder()
                .id(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .commentCount(community.getCommunityComments().size())
                .writer(community.getMember().getNickname())
                .writerProfileImageUrl(community.getMember().getProfile().getProfileImgUrl())
                .scrapCount(community.getCommunityScraps().size())
                .likeCount(community.getCommunityLikes().size())
                .build();
    }
}
