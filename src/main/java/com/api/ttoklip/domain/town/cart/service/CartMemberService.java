package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.cart.domain.CartMember;
import com.api.ttoklip.domain.town.cart.repository.cartMember.CartMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartMemberService {

    private final CartMemberRepository cartMemberRepository;

    @Transactional
    public void register(Cart cart) {
        CartMember newCartMember = CartMember.from(cart);
        cartMemberRepository.save(newCartMember);
    }

    public void delete(Long cartMemberId) {
        cartMemberRepository.deleteById(cartMemberId);
    }

    public List<CartMember> findByCartId(Long cartId) {
        return cartMemberRepository.findByCartId(cartId);
    }

    public Optional<CartMember> findByMemberIdAndCartId(Long currentMemberId, Long cartId) {
        return cartMemberRepository.findByMemberIdAndCartId(currentMemberId, cartId);
    }

    public boolean existsByMemberIdAndCartId(Long memberId, Long cartId) {
        return cartMemberRepository.existsByMemberIdAndCartId(memberId, cartId);
    }
}
