package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import static com.api.ttoklip.domain.town.cart.post.entity.QCart.cart;
import static com.api.ttoklip.domain.town.community.comment.QCommunityComment.communityComment;
import static com.api.ttoklip.domain.town.community.image.entity.QCart;
import static com.api.ttoklip.domain.town.community.comment.QCartComment.cartComment;


@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Cart findByIdFetchJoin(final Long cartPostId) {
        Cart findCart = jpaQueryFactory
                .selectFrom(cart)
                .leftJoin(cart.cartIamges, cartIamge)
                .fetchJoin()
                .where(cart.id.eq(cartPostId))
                .fetchOne();

        return Optional.ofNullable(findCart)
                .orElseThrow(() -> new ApiException(ErrorType.CART_NOT_FOUND));
    }

    @Override
    public List<CartComment> findActiveCommentsByCartId(final Long cartId) {
        return jpaQueryFactory
                .selectFrom(cartComment)
                .where(
                        matchCartId(cartId),
                        getCommentActivate()
                )
                .orderBy(
                        cartComment.createdDate.asc(),
                        cartComment.parent.id.asc().nullsFirst()
                )
                .fetch();
    }

    private BooleanExpression matchCartId(final Long cartId) {
        return cartComment.community.id.eq(cartId);
    }

    private BooleanExpression getCommentActivate() {
        return cartComment.deleted.isFalse();
    }


}
