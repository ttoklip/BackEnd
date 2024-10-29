package com.api.ttoklip.domain.town.community.controller.dto.response;

import com.api.ttoklip.domain.town.community.domain.Community;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CommunityRecent3Response {

    @Schema(description = " ID", example = "1")
    private Long communityId;

    @Schema(description = "소통해요 제목", example = "소통해요 제목 예시")
    private String title;

    @Schema(description = "소통해요 주소", example = "소통해요 주소 예시")
    private String street;

    public static CommunityRecent3Response from(final Community community) {
        return CommunityRecent3Response.builder()
                .communityId(community.getId())
                .title(community.getTitle())
                .street(community.getMember().getStreet())
                .build();
    }
}
