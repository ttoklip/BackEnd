package com.api.ttoklip.domain.town.cart.repository;

import com.api.ttoklip.domain.town.cart.domain.CartMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMemberRepository extends JpaRepository<CartMember, Long>, CartMemberRepositoryCustom {

    boolean existsByMemberIdAndCartId(Long memberId, Long cartId);

    boolean existsCartMemberByMemberIdAndCartId(Long memberId, Long cartId);

    List<CartMember> findByCartId(Long cartId);

    Optional<CartMember> findByMemberIdAndCartId(Long memberId, Long cartId);
}
