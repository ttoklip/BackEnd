package com.domain.cart.infrastructure;

import com.domain.cart.domain.ItemUrl;
import com.domain.cart.domain.ItemUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemUrlRepositoryImpl implements ItemUrlRepository {

    private final ItemUrlJpaRepository jpaRepository;

    @Override
    public ItemUrl save(final ItemUrl itemUrl) {
        return jpaRepository.save(itemUrl);
    }

    @Override
    public void deleteAllByCartId(final Long cartId) {
        jpaRepository.deleteAllByCartId(cartId);
    }
}
