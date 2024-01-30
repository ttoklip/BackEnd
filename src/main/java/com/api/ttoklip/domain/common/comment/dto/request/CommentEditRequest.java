package com.api.ttoklip.domain.common.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommentEditRequest {

    @Schema(description = "댓글 내용", example = "댓글 내용 예시")
    private String comment;

}
