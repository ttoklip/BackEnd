package com.domain.cart.infrastructure;

import com.domain.cart.domain.CartMember;
import com.domain.cart.domain.CartMemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartMemberRepositoryImpl implements CartMemberRepository {

    private final CartMemberJpaRepository jpaRepository;
    private final EntityManager em;

    @Override
    public boolean existsByMemberIdAndCartId(final Long memberId, final Long cartId) {
        return jpaRepository.existsByMemberIdAndCartId(memberId, cartId);
    }

    @Override
    public List<CartMember> findByCartId(final Long cartId) {
        return jpaRepository.findAllById(List.of(cartId));
    }

    @Override
    public Optional<CartMember> findByMemberIdAndCartId(final Long memberId, final Long cartId) {
        return jpaRepository.findByMemberIdAndCartId(memberId, cartId);
    }

    @Override
    public CartMember save(final CartMember cartMember) {
        return jpaRepository.save(cartMember);
    }

    @Override
    public void deleteById(final Long CartMemberId) {
        jpaRepository.deleteById(CartMemberId);
        em.flush();
        em.clear();
    }
}
