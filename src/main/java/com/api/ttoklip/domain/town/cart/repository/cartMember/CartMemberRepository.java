package com.api.ttoklip.domain.town.cart.repository.cartMember;

import com.api.ttoklip.domain.town.cart.domain.CartMember;

import java.util.List;
import java.util.Optional;

public interface CartMemberRepository {

    boolean existsByMemberIdAndCartId(Long memberId, Long cartId);

    List<CartMember> findByCartId(Long cartId);

    Optional<CartMember> findByMemberIdAndCartId(Long memberId, Long cartId);
    
    CartMember save(CartMember cartMember);

    void deleteById(Long cartMemberId);
}
