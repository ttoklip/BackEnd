package com.api.ttoklip.domain.town.cart.post.repository;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.town.cart.comment.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.post.entity.QCart.cart;
import static com.api.ttoklip.domain.town.cart.post.entity.QCartMember.cartMember;

import com.api.ttoklip.domain.town.cart.post.entity.Cart;
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
                .distinct()
                .leftJoin(cart.cartMembers, cartMember)
                .leftJoin(cart.cartComments, cartComment)
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .where(
                        cart.totalPrice.between(startMoney, lastMoney).and(cart.partyMax.between(startParty, lastParty))
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(cart.id.desc())
                .fetch();
    }

    private Long countQuery() {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .fetchOne();
    }
}
