package com.api.ttoklip.domain.town.cart.repository.url;

import com.api.ttoklip.domain.town.cart.domain.ItemUrl;

public interface ItemUrlRepository {

    void deleteAllByCartId(final Long cartId);

    ItemUrl save(ItemUrl newItemUrl);

}
