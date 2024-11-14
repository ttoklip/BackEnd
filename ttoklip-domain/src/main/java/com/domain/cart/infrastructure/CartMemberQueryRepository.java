package com.domain.cart.infrastructure;

import com.domain.cart.domain.Cart;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartMemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Cart> findParticipatingCartsByUserId(final Long userId, final Pageable pageable) {
        List<Cart> content = findParticipatingCarts(userId, pageable);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> findParticipatingCarts(final Long userId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartMembers, cartMember)
                .leftJoin(cart.cartComments, cartComment)
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .where(cartMember.member.id.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(cart.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .where(cartMember.member.id.eq(userId))
                .fetchOne();
    }
}
