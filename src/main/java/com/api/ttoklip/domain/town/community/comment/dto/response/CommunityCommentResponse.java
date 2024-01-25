package com.api.ttoklip.domain.town.community.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommunityCommentResponse {

    private Long commentId;

    private String commentContent;

    private Long parentId;

    private String writer;

    private String writtenTime;
}
