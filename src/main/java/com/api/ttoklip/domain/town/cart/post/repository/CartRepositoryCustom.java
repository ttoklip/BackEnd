package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.post.entity.Cart;


import java.util.List;

public interface CartRepositoryCustom {

    Cart findByIdFetchJoin(final Long cartPostId);
    List<CartComment> findActiveCommentsByQuestionId(final Long commentId);
}
