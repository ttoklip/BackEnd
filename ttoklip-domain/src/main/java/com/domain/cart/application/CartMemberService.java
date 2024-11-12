package com.domain.cart.application;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartMember;
import com.domain.cart.domain.CartMemberRepository;
import com.domain.member.domain.Member;
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
    public void register(Cart cart, Member member) {
        CartMember newCartMember = CartMember.from(cart, member);
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
