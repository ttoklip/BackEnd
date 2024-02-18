package com.api.ttoklip.domain.home.response;

import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.newsletter.main.dto.response.CategoryResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class HomeResponse {

    private String currentMemberNickname;
    private String street;
//    private Object weather;
    private List<TitleResponse> honeyTips;
    private List<CategoryResponse> newsLetters;
//    private Object community;
}
