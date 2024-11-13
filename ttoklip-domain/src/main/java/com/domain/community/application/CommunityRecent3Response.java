package com.domain.community.application;

import com.domain.community.domain.Community;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CommunityRecent3Response(Long communityId, String title, String street) {

    public static CommunityRecent3Response from(final Community community) {
        return CommunityRecent3Response.builder()
                .communityId(community.getId())
                .title(community.getTitle())
                .street(community.getMember().getStreet())
                .build();
    }
}
