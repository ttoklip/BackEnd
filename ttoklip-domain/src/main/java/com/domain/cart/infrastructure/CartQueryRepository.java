package com.domain.cart.infrastructure;

import com.common.exception.ApiException;
import com.common.exception.ErrorType;
import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartComment;
import com.domain.cart.domain.QCart;
import com.domain.cart.domain.QCartComment;
import com.domain.cart.domain.QCartImage;
import com.domain.cart.domain.QCartMember;
import com.domain.cart.domain.QItemUrl;
import com.domain.common.vo.TownCriteria;
import com.domain.interest.domain.QInterest;
import com.domain.member.domain.Member;
import com.domain.member.domain.QMember;
import com.domain.profile.domain.QProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class CartQueryRepository {

    private static final String SPLIT_CRITERIA = " ";
    private static final String POPULARITY = "popularity";
    private static final String LATEST = "latest";

    private final JPAQueryFactory jpaQueryFactory;

    private final QProfile profile = QProfile.profile;
    private final QCart cart = QCart.cart;
    private final QMember member = QMember.member;
    private final QItemUrl itemUrl = QItemUrl.itemUrl;
    private final QCartComment cartComment = QCartComment.cartComment;
    private final QCartMember cartMember = QCartMember.cartMember;

    public Cart findByIdActivated(final Long cartId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .where(
                        matchId(cartId), getCartActivate()
                )
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.interests, QInterest.interest).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .fetchOne();
        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    private BooleanExpression matchId(final Long cartId) {
        return cart.id.eq(cartId);
    }

    private BooleanExpression getCartActivate() {
        return cart.deleted.isFalse();
    }

    public Cart findByIdFetchJoin(final Long cartPostId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartImages, QCartImage.cartImage)
                .leftJoin(cart.itemUrls, itemUrl)
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .where(
                        getCartActivate(),
                        cart.id.eq(cartPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    public List<CartComment> findActiveCommentsByCartId(final Long cartId) {
        return jpaQueryFactory
                .selectFrom(cartComment)
                .where(
                        matchCartId(cartId)
                )
                .leftJoin(cartComment.member, member).fetchJoin()
                .leftJoin(cartComment.member.profile, profile).fetchJoin()
                .orderBy(
                        cartComment.parent.id.asc().nullsFirst(),
                        cartComment.createdDate.asc()
                )
                .fetch();
    }

    private BooleanExpression matchCartId(final Long cartId) {
        return cartComment.cart.id.eq(cartId);
    }

    public Long countParticipants(Long cartId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cartMember)
                .where(cartMember.cart.id.eq(cartId))
                .fetchOne();
    }

    public List<Cart> findRecent3(final TownCriteria townCriteria, final String street) {
        return jpaQueryFactory
                .selectFrom(cart)
                .where(
                        getCartActivate(),
                        getLocationFilterByTownCriteria(townCriteria, street)
                )
                .leftJoin(cart.member, this.member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .orderBy(cart.id.desc())
                .limit(3)
                .fetch();
    }

    public Page<Cart> getCartPaging(final Pageable pageable,
                                    final Long startMoney,
                                    final Long lastMoney,
                                    final Long startParty,
                                    final Long lastParty,
                                    final TownCriteria townCriteria,
                                    final Member member) {
        List<Cart> content = getCartContent(pageable, startMoney, lastMoney, startParty, lastParty, townCriteria,
                member);
        Long count = countQuery(startMoney, lastMoney, startParty, lastParty, townCriteria, member);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> getCartContent(final Pageable pageable,
                                      final Long startMoney,
                                      final Long lastMoney,
                                      final Long startParty,
                                      final Long lastParty,
                                      final TownCriteria townCriteria, final Member member) {

        return jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartMembers, cartMember)
                .leftJoin(cart.cartComments, cartComment)
                .leftJoin(cart.member, this.member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .where(
                        cart.totalPrice.between(startMoney, lastMoney),
                        cart.partyMax.between(startParty, lastParty),
                        getLocationFilterByTownCriteria(townCriteria, member.getStreet())
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(cart.id.desc())
                .fetch();
    }

    private Long countQuery(
            final Long startMoney,
            final Long lastMoney,
            final Long startParty,
            final Long lastParty,
            final TownCriteria townCriteria,
            final Member member) {

        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .where(
                        cart.totalPrice.between(startMoney, lastMoney),
                        cart.partyMax.between(startParty, lastParty),
                        getLocationFilterByTownCriteria(townCriteria, member.getStreet())
                )
                .fetchOne();
    }

    public BooleanExpression getLocationFilterByTownCriteria(final TownCriteria townCriteria,
                                                             final String street) {
        String[] streetParts = splitStreet(street);  // 공통 메서드로 분리

        if (townCriteria.equals(TownCriteria.CITY)) {
            return filterByCity(streetParts);
        }

        if (townCriteria.equals(TownCriteria.DISTRICT)) {
            return filterByDistrict(streetParts);
        }

        if (townCriteria.equals(TownCriteria.TOWN)) {
            return filterByTown(streetParts);
        }

        throw new ApiException(ErrorType.INTERNAL_STREET_TYPE);
    }

    // 주소를 공백으로 분리하는 로직을 하나의 메서드로 추출
    private String[] splitStreet(final String street) {
        return street.split(SPLIT_CRITERIA);
    }

    // '시' 부분만 추출해서 필터링 (예: '서울특별시'로 시작하는 모든 주소)
    private BooleanExpression filterByCity(final String[] streetParts) {

        if (streetParts.length > 0) {
            String city = streetParts[0];
            return cart.member.street.startsWith(city);
        }
        return null;
    }

    // '시'와 '구'가 모두 일치해야 함 (예: '서울특별시 서대문구')
    private BooleanExpression filterByDistrict(final String[] streetParts) {
        if (streetParts.length > 1) {
            String city = streetParts[0];
            String district = streetParts[1];
            return cart.member.street.startsWith(city + SPLIT_CRITERIA + district);
        }
        return null;
    }

    // '시', '구', '동'이 모두 일치해야 함 (예: '서울특별시 서대문구 북가좌동')
    private BooleanExpression filterByTown(final String[] streetParts) {
        if (streetParts.length > 2) {
            String city = streetParts[0];
            String district = streetParts[1];
            String town = streetParts[2];
            return cart.member.street.startsWith(city + SPLIT_CRITERIA + district + SPLIT_CRITERIA + town);
        }
        return null;
    }

    public Page<Cart> searchPaging(final String keyword, final Pageable pageable, final String sort) {
        List<Cart> content = getSearchPageTitleOrContent(keyword, pageable, sort);
        Long count = countQuery(keyword);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> getSearchPageTitleOrContent(final String keyword, final Pageable pageable, final String sort) {
        JPAQuery<Cart> query = defaultQuery(keyword, pageable);

        if (sort.equals(POPULARITY)) {
            return sortPopularity(query);
        }

        if (sort.equals(LATEST)) {
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
                .groupBy(cart.id)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
    }
    private BooleanExpression containTitle(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return cart.title.contains(keyword);
        }
        return Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression containContent(final String keyword) {
        if (StringUtils.hasText(keyword)) {
            return cart.content.contains(keyword);
        }
        return Expressions.asBoolean(true).isTrue();
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
