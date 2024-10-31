package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.cart.repository.CartRepository;
import com.api.ttoklip.domain.town.community.controller.dto.response.CommunityRecent3Response;
import com.api.ttoklip.domain.town.community.domain.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartPostService {

    private final CartRepository cartRepository;

    @Transactional
    public Cart save(final Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findByIdActivated(Long cartId) {
        return cartRepository.findByIdActivated(cartId);
    }

    public Long countParticipants(Long cartId) {
        return cartRepository.countParticipants(cartId);
    }

    public Cart findByIdFetchJoin(Long postId) {
        return cartRepository.findByIdFetchJoin(postId);
    }

    public List<Cart> findRecent3(TownCriteria townCriteria) {
        return cartRepository.findRecent3(townCriteria);
    }

    public Page<Cart> getCartPaging(Pageable pageable,
                                    Long startMoney,
                                    Long lastMoney,
                                    Long startParty,
                                    Long lastParty,
                                    TownCriteria townCriteria) {
        return cartRepository.getCartPaging(
                pageable,
                startMoney,
                lastMoney,
                startParty,
                lastParty,
                townCriteria);
    }

    public List<UserCartSingleResponse> getRecent3(TownCriteria townCriteria) {
        List<Cart> carts = cartRepository.findRecent3(townCriteria);
        return carts.stream()
                .map(UserCartSingleResponse::from)
                .toList();
    }

}
