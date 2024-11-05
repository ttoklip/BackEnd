package com.api.ttoklip.domain.town.cart.repository.post;

import com.api.ttoklip.domain.privacy.domain.QInterest;
import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import com.api.ttoklip.domain.town.cart.domain.CartComment;
import com.api.ttoklip.domain.town.cart.domain.QCartImage;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.api.ttoklip.domain.member.domain.QMember.member;
import static com.api.ttoklip.domain.privacy.domain.QProfile.profile;
import static com.api.ttoklip.domain.town.cart.domain.QCart.cart;
import static com.api.ttoklip.domain.town.cart.domain.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.domain.QCartMember.cartMember;
import static com.api.ttoklip.domain.town.cart.domain.QItemUrl.itemUrl;
import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Repository
@RequiredArgsConstructor
public class CartQueryRepository {

    private static final String SPLIT_CRITERIA = " ";
    private final JPAQueryFactory jpaQueryFactory;

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

    // 참여자 추가
    public Cart addParticipant(final Long cartId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .where(cart.id.eq(cartId))
                .fetchOne();
        Long memberId = getCurrentMember().getId();

        // ToDo 직접적으로 쿼리로 추가하는 것이 아닌 엔티티 생성 후 연관관계 매핑으로 해결할 것
        jpaQueryFactory
                .insert(cartMember)
                .columns(cartMember.cart.id, cartMember.member.id)
                .values(cartId, memberId)
                .execute();

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    // 참여 취소
    public Cart removeParticipant(final Long cartId) {
        jpaQueryFactory
                .delete(cartMember)
                .where(cartMember.cart.id.eq(cartId))
                .execute();

        Cart findCart = findByIdActivated(cartId);

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    // 참여자 수 확인
    public Long countParticipants(Long cartId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cartMember)
                .where(cartMember.cart.id.eq(cartId))
                .fetchOne();
    }

    public List<Cart> findRecent3(final TownCriteria townCriteria) {
        String writerStreet = getCurrentMember().getStreet();
        return jpaQueryFactory
                .selectFrom(cart)
                .where(
                        getCartActivate(),
                        getLocationFilterByTownCriteria(townCriteria, writerStreet)
                )
                .leftJoin(cart.member, member).fetchJoin()
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
                                    final TownCriteria townCriteria
    ) {
        List<Cart> content = getCartContent(pageable, startMoney, lastMoney, startParty, lastParty, townCriteria);
        Long count = countQuery(startMoney, lastMoney, startParty, lastParty, townCriteria);
        return new PageImpl<>(content, pageable, count);
    }

    private List<Cart> getCartContent(final Pageable pageable,
                                      final Long startMoney,
                                      final Long lastMoney,
                                      final Long startParty,
                                      final Long lastParty,
                                      final TownCriteria townCriteria) {
        String writerStreet = getCurrentMember().getStreet();

        return jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartMembers, cartMember)
                .leftJoin(cart.cartComments, cartComment)
                .leftJoin(cart.member, member).fetchJoin()
                .leftJoin(cart.member.profile, profile).fetchJoin()
                .where(
                        cart.totalPrice.between(startMoney, lastMoney),
                        cart.partyMax.between(startParty, lastParty),
                        getLocationFilterByTownCriteria(townCriteria, writerStreet)
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
            final TownCriteria townCriteria
    ) {
        String writerStreet = getCurrentMember().getStreet();
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cart)
                .where(
                        cart.totalPrice.between(startMoney, lastMoney),
                        cart.partyMax.between(startParty, lastParty),
                        getLocationFilterByTownCriteria(townCriteria, writerStreet)
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
}
