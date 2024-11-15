package com.domain.cart.infrastructure;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.QCart;
import com.domain.cart.domain.QCartComment;
import com.domain.cart.domain.QCartMember;
import com.domain.member.domain.QMember;
import com.domain.profile.domain.QProfile;
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

    private final QCart cart = QCart.cart;
    private final QCartComment cartComment = QCartComment.cartComment;
    private final QMember member = QMember.member;
    private final QCartMember cartMember = QCartMember.cartMember;
    private final QProfile profile = QProfile.profile;

    public Page<Cart> findParticipatingCartsByUserId(final Long userId, final Pageable pageable) {
        List<Cart> content = findParticipatingCarts(userId, pageable);
        Long count = countQuery(userId);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> findParticipatingCarts(final Long memberId, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartMembers, cartMember)
                .leftJoin(cart.cartComments, cartComment)
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .where(cartMember.member.id.eq(memberId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(cart.id.desc())
                .fetch();
    }

    private Long countQuery(final Long memberId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .where(cartMember.member.id.eq(memberId))
                .fetchOne();
    }
}
