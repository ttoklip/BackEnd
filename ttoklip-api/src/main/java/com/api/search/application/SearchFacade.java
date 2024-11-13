package com.api.search.application;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import com.api.search.presentation.response.SingleResponse;
import com.api.town.application.TownCommunityResponse;
import com.domain.cart.application.CartPostService;
import com.domain.cart.application.CartRecent3Response;
import com.domain.cart.domain.Cart;
import com.domain.community.application.CommunityPostService;
import com.domain.community.domain.Community;
import com.domain.honeytip.application.HoneyTipPostService;
import com.domain.honeytip.domain.HoneyTip;
import com.domain.newsletter.application.NewsletterPostService;
import com.domain.newsletter.domain.Newsletter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchFacade {

    private final HoneyTipPostService honeyTipPostService;
    private final CommunityPostService communityPostService;
    private final CartPostService cartPostService;
    private final NewsletterPostService newsletterPostService;

    public HoneyTipPaging honeyTipSearch(final String keyword, final Pageable pageable, final String sort) {
        Page<HoneyTip> contentPaging = honeyTipPostService.getContain(keyword, pageable, sort);
        List<HoneyTip> contents = contentPaging.getContent();
        List<SingleResponse> honeyTipSingleData = contents.stream()
                .map(SingleResponse::honeyTipFrom)
                .toList();

        return HoneyTipPaging.builder()
                .honeyTips(honeyTipSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public NewsletterPaging newsletterPaging(final String keyword, final Pageable pageable, final String sort) {
        Page<Newsletter> contentPaging = newsletterPostService.getContain(keyword, pageable, sort);
        List<Newsletter> contents = contentPaging.getContent();
        List<SingleResponse> newsletterSingleData = contents.stream()
                .map(SingleResponse::newsletterFrom)
                .toList();

        return NewsletterPaging.builder()
                .newsletters(newsletterSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CommunityPaging communityPaging(final String keyword, final Pageable pageable, final String sort) {
        Page<Community> contentPaging = communityPostService.getContain(keyword, pageable, sort);
        List<Community> contents = contentPaging.getContent();
        List<TownCommunityResponse> communitySingleData = contents.stream()
                .map(TownCommunityResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CartPaging cartPaging(final String keyword, final Pageable pageable, final String sort) {
        Page<Cart> contentPaging = cartPostService.getContain(keyword, pageable, sort);
        List<Cart> contents = contentPaging.getContent();
        List<CartRecent3Response> cartSingleData = contents.stream()
                .map(CartRecent3Response::from)
                .toList();

        return CartPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }
}
