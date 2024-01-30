package com.api.ttoklip.domain.town.community.dto.response;

import com.api.ttoklip.domain.town.community.Community;
import com.api.ttoklip.domain.town.community.comment.dto.response.CommunityCommentResponse;
import com.api.ttoklip.domain.town.community.image.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
