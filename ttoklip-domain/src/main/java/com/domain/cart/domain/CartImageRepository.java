package com.domain.cart.domain;

public interface CartImageRepository {
    void deleteAllByCartId(final Long cartId);

    CartImage save(CartImage newCartImage);
}
