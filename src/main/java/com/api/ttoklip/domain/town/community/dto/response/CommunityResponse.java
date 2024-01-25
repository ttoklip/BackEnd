package com.api.ttoklip.domain.town.community.dto.response;

import com.api.ttoklip.domain.town.community.comment.dto.response.CommunityCommentResponse;
//import com.api.ttoklip.domain.town.community.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommunityResponse {

    private Long id;
    private String title;
    private String content;
    private String createdBy;

//    private List<Image> images;

    private List<CommunityCommentResponse> commCommentResponses;
}
