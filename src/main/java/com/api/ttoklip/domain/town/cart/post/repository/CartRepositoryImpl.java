package com.api.ttoklip.domain.town.cart.post.repository;

import static com.api.ttoklip.domain.town.cart.comment.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.image.entity.QCartImage.cartImage;
import static com.api.ttoklip.domain.town.cart.itemUrl.entity.QItemUrl.itemUrl;
import static com.api.ttoklip.domain.town.cart.post.entity.QCart.cart;
import static com.api.ttoklip.domain.town.cart.post.entity.QCartMember.cartMember;
import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.api.ttoklip.global.util.SecurityUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Cart findByIdActivated(final Long cartId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .where(
                        matchId(cartId), getCartActivate()
                )
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

    @Override
    public Cart findByIdFetchJoin(final Long cartPostId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .distinct()
                .leftJoin(cart.cartImages, cartImage)
                .leftJoin(cart.itemUrls, itemUrl)
                .where(
                        getCartActivate(),
                        cart.id.eq(cartPostId)
                )
                .fetchOne();

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    @Override
    public List<CartComment> findActiveCommentsByCartId(final Long cartId) {
        return jpaQueryFactory
                .selectFrom(cartComment)
                .where(
                        matchCartId(cartId)
                )
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
    @Override
    public Cart addParticipant(final Long cartId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .where(cart.id.eq(cartId))
                .fetchOne();
        Long memberId = getCurrentMember().getId();

        jpaQueryFactory
                .insert(cartMember)
                .columns(cartMember.cart.id, cartMember.member.id)
                .values(cartId, memberId)
                .execute();

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    // 참여 취소
    @Override
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
    @Override
    public Long countParticipants(Long cartId) {
        return jpaQueryFactory
                .select(Wildcard.count)
                .from(cartMember)
                .where(cartMember.cart.id.eq(cartId))
                .fetchOne();
    }

    @Override
    public List<Cart> findRecent3() {
        return jpaQueryFactory
                .selectFrom(cart)
                .where(
                        getCartActivate()
                )
                .orderBy(cart.id.desc())
                .limit(3)
                .fetch();
    }
}