package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;


import java.util.List;

public interface CartRepositoryCustom {

    Cart findByIdActivated(final Long cartId);

    Cart findByIdFetchJoin(final Long postId);

    List<CartComment> findActiveCommentsByCartId(final Long postId);

    // 참여자 추가
    Cart addParticipant(Long cartId, Long memberId);

    // 참여 취소
    Cart removeParticipant(Long cartId, Long memberId);

    // 참여자 수 확인
    int countParticipants(Long cartId);

}
