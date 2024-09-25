package com.api.ttoklip.domain.home.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.home.response.HomeResponse;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterThumbnailResponse;
import com.api.ttoklip.domain.newsletter.post.service.NewsletterPostService;
import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final CartPostService cartPostService;
    private final HoneyTipPostService honeyTipPostService;
    private final NewsletterPostService newsletterPostService;

    @Transactional
    public HomeResponse home() {
        List<TitleResponse> honeyTipRecent3 = honeyTipPostService.getRecent3();
        List<NewsletterThumbnailResponse> newsletterRecent3 = newsletterPostService.getRecent3();

        List<UserCartSingleResponse> cartRecent3 = cartPostService.getRecent3(TownCriteria.CITY);

        return HomeResponse.builder()
                .currentMemberNickname(getCurrentMember().getNickname())
                .street(getCurrentMember().getStreet())
                .honeyTips(honeyTipRecent3)
                .newsLetters(newsletterRecent3)
                .carts(cartRecent3)
                .build();
    }
}
