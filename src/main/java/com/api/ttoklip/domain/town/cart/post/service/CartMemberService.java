package com.api.ttoklip.domain.town.cart.post.service;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.entity.CartMember;
import com.api.ttoklip.domain.town.cart.post.repository.CartMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;


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
}
