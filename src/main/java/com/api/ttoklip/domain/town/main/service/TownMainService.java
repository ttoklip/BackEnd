package com.api.ttoklip.domain.town.main.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.mypage.main.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.search.response.CartSearchPaging;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.CommunitySingleResponse;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.repository.CartSearchRepository;
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

    private final CartSearchRepository cartSearchRepository;
    private final CommunityPostService communityPostService;
    private final CartPostService cartPostService;

    public CommunityPaging getCommunities(final Pageable pageable) {

        Page<Community> contentPaging = communityPostService.getPaging(pageable);

        // List<Entity>
        List<Community> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
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

    public CartSearchPaging getCarts(final Pageable pageable,
                                     final Long startMoney,
                                     final Long lastMoney,
                                     final Long startParty,
                                     final Long lastParty) {

        Page<Cart> contentPaging = cartSearchRepository.getContain(pageable, startMoney, lastMoney, startParty,
                lastParty);

        // List<Entity>
        List<Cart> contents = contentPaging.getContent();

        // Entity -> SingleResponse 반복
        List<UserCartSingleResponse> cartSingleData = contents.stream()
                .map(UserCartSingleResponse::cartFrom)
                .toList();

        return CartSearchPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();

    }

    public CartMainResponse getRecent3() {
        List<UserCartSingleResponse> cartRecent3 = cartPostService.getRecent3();
        List<CommunityRecent3Response> communityRecent3 = communityPostService.getRecent3();
        String street = getCurrentMember().getStreet();

        return CartMainResponse.builder()
                .cartRecent3(cartRecent3)
                .communityRecent3(communityRecent3)
                .street(street)
                .build();
    }
}
