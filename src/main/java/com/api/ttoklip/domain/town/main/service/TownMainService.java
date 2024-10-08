package com.api.ttoklip.domain.town.main.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.search.response.CartPaging;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import com.api.ttoklip.domain.town.community.post.dto.response.CartMainResponse;
import com.api.ttoklip.domain.town.community.post.dto.response.CommunityRecent3Response;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.community.post.service.CommunityPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TownMainService {

    private final CommunityPostService communityPostService;
    private final CartPostService cartPostService;

    public CommunityPaging getCommunities(final String criteria, final Pageable pageable) {
        TownCriteria townCriteria = validCriteria(criteria);

        Page<Community> contentPaging = communityPostService.getPaging(townCriteria, pageable);

        List<Community> contents = contentPaging.getContent();

        List<CommunitySingleResponse> communitySingleData = contents.stream()
                .map(CommunitySingleResponse::communityFrom)
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

        // List<Entity>
        List<Cart> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<UserCartSingleResponse> cartSingleData = contents.stream()
                .map(UserCartSingleResponse::cartFrom)
                .toList();

        return CartPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();
    }

    public CartMainResponse getRecent3(final String criteria) {
        TownCriteria townCriteria = validCriteria(criteria);
        List<UserCartSingleResponse> cartRecent3 = cartPostService.getRecent3(townCriteria);
        List<CommunityRecent3Response> communityRecent3 = communityPostService.getRecent3(townCriteria);
        String street = getCurrentMember().getStreet();

        return CartMainResponse.builder()
                .cartRecent3(cartRecent3)
                .communityRecent3(communityRecent3)
                .street(street)
                .build();
    }
}
