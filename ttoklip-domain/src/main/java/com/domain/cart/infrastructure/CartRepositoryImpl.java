package com.domain.cart.infrastructure;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartComment;
import com.domain.cart.domain.CartRepository;
import com.domain.common.vo.TownCriteria;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository jpaRepository;
//    private final CartQueryRepository queryRepository;

    @Override
    public Cart save(final Cart cart) {
        return jpaRepository.save(cart);
    }

    @Override
    public Cart findByIdActivated(final Long cartId) {
//        return queryRepository.findByIdActivated(cartId);
        return null;
    }

    @Override
    public Cart findByIdFetchJoin(final Long cartPostId) {
//        return queryRepository.findByIdFetchJoin(cartPostId);
        return null;
    }

    @Override
    public List<CartComment> findActiveCommentsByCartId(final Long cartId) {
//        return queryRepository.findActiveCommentsByCartId(cartId);
        return null;
    }

    @Override
    public Cart addParticipant(final Long cartId) {
//        return queryRepository.addParticipant(cartId);
        return null;
    }

    @Override
    public Cart removeParticipant(final Long cartId) {
//        return queryRepository.removeParticipant(cartId);
        return null;
    }

    @Override
    public Long countParticipants(final Long cartId) {
//        return queryRepository.countParticipants(cartId);
        return null;
    }

    @Override
    public List<Cart> findRecent3(final TownCriteria townCriteria) {
//        return queryRepository.findRecent3(townCriteria);
        return null;
    }

    @Override
    public Page<Cart> getCartPaging(final Pageable pageable,
                                    final Long startMoney,
                                    final Long lastMoney,
                                    final Long startParty,
                                    final Long lastParty,
                                    final TownCriteria townCriteria) {
//        return queryRepository.getCartPaging(pageable, startMoney, lastMoney, startParty, lastParty, townCriteria);
        return null;
    }

    @Override
    public Page<Cart> searchPaging(final String keyword, final Pageable pageable, final String sort) {
//        return queryRepository.searchPaging(keyword, pageable, sort);
        return null;
    }
}
