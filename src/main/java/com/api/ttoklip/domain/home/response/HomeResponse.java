package com.api.ttoklip.domain.home.response;

import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
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
    private String street;
    private String todayToDoList;
    private List<TitleResponse> honeyTips;
    private List<NewsletterThumbnailResponse> newsLetters;
    private List<UserCartSingleResponse> carts;
}
