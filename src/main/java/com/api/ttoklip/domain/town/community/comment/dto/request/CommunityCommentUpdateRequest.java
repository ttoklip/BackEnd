package com.api.ttoklip.domain.town.community.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CommunityCommentUpdateRequest {
    private String comment;
}
