package com.api.ttoklip.domain.town.cart.repository.image;

import com.api.ttoklip.domain.town.cart.domain.CartImage;

public interface CartImageRepository {
    void deleteAllByCartId(final Long cartId);

    CartImage save(CartImage newCartImage);
}
