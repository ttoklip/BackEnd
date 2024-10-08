package com.api.ttoklip.domain.town.cart.post.repository;

import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.comment.CartComment;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartRepositoryCustom {

    Cart findByIdActivated(final Long cartId);

    Cart findByIdFetchJoin(final Long postId);

    List<CartComment> findActiveCommentsByCartId(final Long postId);

    // 참여자 추가
    Cart addParticipant(final Long cartId);

    // 참여 취소
    Cart removeParticipant(final Long cartId);

    // 참여자 수 확인
    Long countParticipants(Long cartId);

    List<Cart> findRecent3(final TownCriteria townCriteria);

    Page<Cart> getCartPaging(
            final Pageable pageable,
            final Long startMoney,
            final Long lastMoney,
            final Long startParty,
            final Long lastParty,
            final TownCriteria townCriteria
    );
}
