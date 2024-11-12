package com.domain.cart.infrastructure;

import com.domain.cart.domain.CartMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartMemberJpaRepository extends JpaRepository<CartMember, Long> {

    boolean existsByMemberIdAndCartId(Long memberId, Long cartId);

    List<CartMember> findByCartId(Long cartId);

    Optional<CartMember> findByMemberIdAndCartId(Long memberId, Long cartId);

    CartMember save(CartMember cartMember);

    void delete(CartMember cartMember);

}
