package com.api.ttoklip.domain.town.community.post.dto.response;

import com.api.ttoklip.domain.town.community.post.entity.Community;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityResponse {

    private String title;
    private String content;

    public CommunityResponse(Community community) {
        this.title = community.getTitle();
        this.content = community.getContent();
    }
}
