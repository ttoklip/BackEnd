package com.api.town.application;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.domain.cart.application.CartThumbnailResponse;
import com.domain.community.application.CommunityRecent3Response;
import com.domain.cart.application.CartPostService;
import com.domain.cart.domain.Cart;
import com.domain.common.vo.TownCriteria;
import com.domain.community.application.CommunityPostService;
import com.domain.community.domain.Community;
import com.domain.member.application.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TownFacade {

    private final CommunityPostService communityPostService;
    private final CartPostService cartPostService;
    private final MemberService memberService;

    public CommunityPaging getCommunities(final String criteria, final Pageable pageable) {
        TownCriteria townCriteria = validCriteria(criteria);

        Page<Community> contentPaging = communityPostService.getPaging(townCriteria, pageable);

        List<Community> contents = contentPaging.getContent();

        List<TownThumbnailResponse> communitySingleData = contents.stream()
                .map(TownThumbnailResponse::from)
                .toList();

        return CommunityPaging.builder()
                .communities(communitySingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();

    }

    private TownCriteria validCriteria(final String criteria) {
        return TownCriteria.findTownCriteriaByValue(criteria);
    }

    public CartPaging getCarts(
            final Pageable pageable,
            final Long startMoney,
            final Long lastMoney,
            final Long startParty,
            final Long lastParty,
            final String criteria
    ) {
        TownCriteria townCriteria = TownCriteria.findTownCriteriaByValue(criteria);

        Page<Cart> contentPaging = cartPostService.getCartPaging(
                pageable, startMoney, lastMoney, startParty, lastParty, townCriteria
        );

        List<Cart> contents = contentPaging.getContent();
        List<CartThumbnailResponse> cartSingleData = contents.stream()
                .map(CartThumbnailResponse::from)
                .toList();

        return CartPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public TownMainResponse getRecent3(final String criteria, final Long currentMemberId) {
        TownCriteria townCriteria = validCriteria(criteria);
        List<CartThumbnailResponse> cartRecent3 = cartPostService.getRecent3(townCriteria);
        List<CommunityRecent3Response> communityRecent3 = communityPostService.getRecent3(townCriteria);
        String street = memberService.getById(currentMemberId).getStreet();

        return TownMainResponse.builder()
                .cartRecent3(cartRecent3)
                .communityRecent3(communityRecent3)
                .street(street)
                .build();
    }
}
