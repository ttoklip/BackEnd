package com.domain.cart.application;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartRepository;
import com.domain.common.vo.TownCriteria;
import com.domain.member.domain.Member;
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

    public Page<Cart> getCartPaging(Pageable pageable,
                                    Long startMoney,
                                    Long lastMoney,
                                    Long startParty,
                                    Long lastParty,
                                    TownCriteria townCriteria,
                                    Member member) {
        return cartRepository.getCartPaging(
                pageable,
                startMoney,
                lastMoney,
                startParty,
                lastParty,
                townCriteria,
                member
        );
    }

    public List<CartThumbnailResponse> getRecent3(TownCriteria townCriteria, final String street) {
        List<Cart> carts = cartRepository.findRecent3(townCriteria, street);
        return carts.stream()
                .map(CartThumbnailResponse::from)
                .toList();
    }

    public Page<Cart> getContain(final String keyword, final Pageable pageable, final String sort) {
        return cartRepository.searchPaging(keyword, pageable, sort);
    }
}
