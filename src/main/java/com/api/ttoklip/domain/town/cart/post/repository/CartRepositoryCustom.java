package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;


import java.util.List;

public interface CartRepositoryCustom {

    Cart findByIdActivated(final Long cartId);

    Cart findByIdFetchJoin(Long postId);

    List<CartComment> findActiveCommentsByCartId(Long postId);

}
