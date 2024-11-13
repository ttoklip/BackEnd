package com.domain.cart.domain;

import com.domain.common.vo.TownCriteria;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartRepository {
    Cart save(Cart cart);

    Cart findByIdActivated(Long cartId);

    Cart findByIdFetchJoin(Long postId);

    List<CartComment> findActiveCommentsByCartId(Long postId);

    // 참여자 추가
    Cart addParticipant(Long cartId);

    // 참여 취소
    Cart removeParticipant(Long cartId);

    // 참여자 수 확인
    Long countParticipants(Long cartId);

    List<Cart> findRecent3(TownCriteria townCriteria);

    Page<Cart> getCartPaging(
            Pageable pageable,
            Long startMoney,
            Long lastMoney,
            Long startParty,
            Long lastParty,
            TownCriteria townCriteria
    );

    Page<Cart> searchPaging(String keyword, Pageable pageable, String sort);
}
