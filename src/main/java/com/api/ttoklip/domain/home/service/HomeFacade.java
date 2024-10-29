package com.api.ttoklip.domain.home.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.home.response.HomeResponse;
import com.api.ttoklip.domain.honeytip.domain.HoneyTip;
import com.api.ttoklip.domain.honeytip.service.HoneyTipPostService;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.newsletter.controller.dto.response.NewsletterThumbnailResponse;
import com.api.ttoklip.domain.newsletter.domain.Newsletter;
import com.api.ttoklip.domain.newsletter.service.NewsletterPostService;
import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.service.CartPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeFacade {

    private final CartPostService cartPostService;
    private final HoneyTipPostService honeyTipPostService;
    private final NewsletterPostService newsletterPostService;

    public HomeResponse home() {
        List<TitleResponse> honeyTipRecent3 = getHoneyTipRecent3();
        List<NewsletterThumbnailResponse> newsletterRecent3 = getNewsletterThumbnailRecent3();
        List<UserCartSingleResponse> cartRecent3 = cartPostService.getRecent3(TownCriteria.CITY);

        return HomeResponse.builder()
                .currentMemberNickname(getCurrentMember().getNickname())
                .street(getCurrentMember().getStreet())
                .honeyTips(honeyTipRecent3)
                .newsLetters(newsletterRecent3)
                .carts(cartRecent3)
                .build();
    }

    public List<TitleResponse> getHoneyTipRecent3() {
        List<HoneyTip> recent3HoneyTip = honeyTipPostService.findRecent3();
        return recent3HoneyTip.stream()
                .map(TitleResponse::honeyTipFrom)
                .toList();
    }

    public List<NewsletterThumbnailResponse> getNewsletterThumbnailRecent3() {
        List<Newsletter> newsletters = newsletterPostService.getRecent3();

        return newsletters.stream()
                .map(NewsletterThumbnailResponse::from)
                .toList();
    }
}
