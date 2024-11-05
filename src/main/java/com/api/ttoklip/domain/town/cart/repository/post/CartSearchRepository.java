package com.api.ttoklip.domain.town.cart.repository.post;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.town.cart.domain.QCart.cart;
import static com.api.ttoklip.domain.town.cart.domain.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.domain.QCartMember.cartMember;

import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class CartSearchRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Cart> getContain(final String keyword, final Pageable pageable, final String sort) {
        List<Cart> content = getSearchPageTitleOrContent(keyword, pageable, sort);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> getSearchPageTitleOrContent(final String keyword, final Pageable pageable, final String sort) {
        JPAQuery<Cart> query = defaultQuery(keyword, pageable);

        if (sort.equals("popularity")) {
            return sortPopularity(query);
        }

        if (sort.equals("latest")) {
            return sortLatest(query);
        }

        throw new ApiException(ErrorType.INVALID_SORT_TYPE);
    }

    private JPAQuery<Cart> defaultQuery(final String keyword, final Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .where(
                        containTitle(keyword).or(containContent(keyword)),
                        getCartActivate()
                )
                .leftJoin(cart.cartComments, cartComment)
                .leftJoin(cart.cartMembers, cartMember)
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }

    private BooleanExpression getCartActivate() {
        return cart.deleted.isFalse();
    }

    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return cart.title.contains(keyword);
        }
        return null;
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return cart.content.contains(keyword);
        }
        return null;
    }

    private List<Cart> sortPopularity(final JPAQuery<Cart> query) {
        // 댓글, 사람 수에 따라 인기 점수 계산
        return query
                .groupBy(cart.id)
                .orderBy(
                        getCommentSize().add(
                                getMemberSize()
                        ).desc()
                ).fetch();
    }

    private NumberExpression<Integer> getCommentSize() {
        return cart.cartComments.size();
    }

    private NumberExpression<Integer> getMemberSize() {
        return cart.cartMembers.size();
    }

    private List<Cart> sortLatest(final JPAQuery<Cart> query) {
        return query
                .orderBy(cart.id.desc())
                .fetch();
    }

    private Long countQuery(final String keyword) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .where(
                        containTitle(keyword).or(containContent(keyword)),
                        getCartActivate()
                )
                .fetchOne();
    }
}
