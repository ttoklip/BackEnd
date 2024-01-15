package com.api.ttoklip.domain.town.dto.response;

import com.api.ttoklip.domain.town.comment.dto.response.CartCommentResponse;
import com.api.ttoklip.domain.town.comment.dto.response.CommCommentResponse;
import com.api.ttoklip.domain.town.image.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommResponse {

    private Long commId;
    private String title;
    private String content;
    private String nickname;
    private String location;
    private String createdBy;

    private List<ImageResponse> imageUrls;

    private List<CommCommentResponse> commCommentResponses;
}
