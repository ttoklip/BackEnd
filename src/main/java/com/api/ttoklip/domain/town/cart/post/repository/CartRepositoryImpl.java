package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.entity.QCartMember;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.api.ttoklip.domain.town.cart.comment.QCartComment.cartComment;
import static com.api.ttoklip.domain.town.cart.image.entity.QCartImage.cartImage;
import static com.api.ttoklip.domain.town.cart.itemUrl.entity.QItemUrl.itemUrl;
import static com.api.ttoklip.domain.town.cart.post.entity.QCart.cart;
import static com.api.ttoklip.domain.town.cart.post.entity.QCartMember.cartMember;


@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Cart findByIdActivated(final Long cartId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .distinct()
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
                .fetchJoin()
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
                .distinct()
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
    public Cart addParticipant(final Long cartId, final Long memberId) {
        jpaQueryFactory
                .insert(cartMember)
                .columns(cartMember.id, cartMember.member.id)
                .values(cartId, memberId)
                .execute();

        Cart findCart = findByIdActivated(cartId);

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    // 참여자 제거
    @Override
    public Cart removeParticipant(final Long cartId, final Long memberId) {
        jpaQueryFactory
                .delete(cartMember)
                .where(cartMember.cart.id.eq(cartId),
                        cartMember.member.id.eq(memberId))
                .execute();

        Cart findCart = findByIdActivated(cartId);

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    // 참여자 수 확인
    @Override
    public int countParticipants(Long cartId) {
        return (int) jpaQueryFactory
                .selectFrom(cartMember)
                .where(cartMember.cart.id.eq(cartId))
                .fetchCount();
    }
}