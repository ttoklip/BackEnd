package com.api.home.presentation.response;

import com.domain.cart.application.CartThumbnailResponse;
import com.domain.common.vo.TitleResponse;
import com.domain.newsletter.application.response.NewsletterThumbnailResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record HomeMainResponse(
        String currentMemberNickname,
        String street,
        List<TitleResponse> honeyTips,
        List<NewsletterThumbnailResponse> newsLetters,
        List<CartThumbnailResponse> carts
) {

}
