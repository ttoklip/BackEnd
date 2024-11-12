package com.domain.cart.domain;

public interface ItemUrlRepository {

    void deleteAllByCartId(final Long cartId);

    ItemUrl save(ItemUrl newItemUrl);

}
