package com.domain.cart.infrastructure;

import com.domain.cart.domain.CartImage;
import com.domain.cart.domain.CartImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartImageRepositoryImpl implements CartImageRepository {

    private final CartImageJpaRepository jpaRepository;

    @Override
    public CartImage save(final CartImage cartImage) {
        return jpaRepository.save(cartImage);
    }

    @Override
    public void deleteAllByCartId(final Long cartId) {
        jpaRepository.deleteAllByCartId(cartId);
    }
}

