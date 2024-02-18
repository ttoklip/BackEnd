package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.post.entity.CartMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartMemberRepository extends JpaRepository<CartMember, Long>, CartMemberRepositoryCustom {

    boolean existsByMemberIdAndCartId(Long memberId, Long cartId);

    boolean existsCartMemberByMemberIdAndCartId(Long memberId, Long cartId);

    List<CartMember> findByCartId(Long cartId);

    Optional<CartMember> findByMemberIdAndCartId(Long memberId, Long cartId);
}
