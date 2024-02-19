package com.api.ttoklip.domain.home.response;

import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterThumbnailResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class HomeResponse {

    private String currentMemberNickname;
//    private String street;
//    private Object weather;
    private List<TitleResponse> honeyTips;
    private List<NewsletterThumbnailResponse> newsLetters;
    private String todayToDoList;
//    private Object community;
}
