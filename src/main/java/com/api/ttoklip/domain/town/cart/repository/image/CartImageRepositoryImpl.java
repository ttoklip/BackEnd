package com.api.ttoklip.domain.town.cart.repository.image;

import com.api.ttoklip.domain.town.cart.domain.CartImage;
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

