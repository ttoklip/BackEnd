package com.api.ttoklip.domain.town.main.service;

import com.api.ttoklip.domain.mypage.main.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.search.response.*;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.community.post.entity.Community;
import com.api.ttoklip.domain.town.main.repository.CartPageRepository;
import com.api.ttoklip.domain.town.main.repository.CommunityPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownMainService {

    private final CommunityPageRepository communityPageRepository;
    private final CartPageRepository cartPageRepository;

    public CommunityPaging getCommunities(final Pageable pageable) {

        Page<Community> contentPaging = communityPageRepository.findAllByOrderByIdDesc(pageable);

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

    public CartSearchPaging getCarts(final Pageable pageable) {

        Page<Cart> contentPaging = cartPageRepository.findAllByOrderByIdDesc(pageable);

        System.out.println("--------------------49");
        // List<Entity>
        List<Cart> contents = contentPaging.getContent();

        System.out.println("--------------------53");

        // Entity -> SingleResponse 반복
        List<UserCartSingleResponse> cartSingleData = contents.stream()
                .map(UserCartSingleResponse::cartFrom)
                .toList();

        System.out.println("--------------------60");

        return CartSearchPaging.builder()
                .carts(cartSingleData)
                .isFirst(contentPaging.isFirst())
                .isLast(contentPaging.isLast())
                .totalElements(contentPaging.getTotalElements())
                .totalPage(contentPaging.getTotalPages())
                .build();

    }

}
