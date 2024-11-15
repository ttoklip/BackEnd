package com.domain.cart.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartMemberRepository {

    boolean existsByMemberIdAndCartId(Long memberId, Long cartId);

    List<CartMember> findByCartId(Long cartId);

    Optional<CartMember> findByMemberIdAndCartId(Long memberId, Long cartId);
    
    CartMember save(CartMember cartMember);

    void deleteById(Long cartMemberId);

    Page<Cart> findParticipatingCartsByUserId(Long userId, Pageable pageable);
}
