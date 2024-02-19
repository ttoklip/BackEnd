package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.api.ttoklip.domain.town.cart.comment.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.post.entity.QCart.cart;
import static com.api.ttoklip.domain.town.cart.post.entity.QCartMember.cartMember;

@Repository
@RequiredArgsConstructor
public class CartSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Cart> getContain(final Pageable pageable,
                                 final Long startMoney,
                                 final Long lastMoney,
                                 final Long startParty,
                                 final Long lastParty) {
        List<Cart> content = getSearchCart(pageable, startMoney, lastMoney, startParty, lastParty);
        Long count = countQuery();
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> getSearchCart(final Pageable pageable,
                                     final Long startMoney,
                                     final Long lastMoney,
                                     final Long startParty,
                                     final Long lastParty) {
            return jpaQueryFactory
                    .selectFrom(cart)
                    .leftJoin(cart.cartMembers, cartMember)
                    .where(
                            cart.totalPrice.between(startMoney,lastMoney).and(cart.partyMax.between(startParty,lastParty))
                    )
                    .distinct()
                    .leftJoin(cart.cartComments, cartComment)
                    .fetchJoin()
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .orderBy(cart.id.desc())
                    .fetch();
    }
    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .distinct()
                .fetchOne();
    }
}
