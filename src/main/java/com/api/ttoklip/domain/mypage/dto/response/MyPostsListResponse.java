package com.api.ttoklip.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class MyPostsListResponse {
    private List<MyPostsResponse> myPostsResponses;
}
