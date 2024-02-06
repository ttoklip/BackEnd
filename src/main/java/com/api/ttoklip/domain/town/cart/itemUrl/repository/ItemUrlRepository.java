package com.api.ttoklip.domain.town.cart.itemUrl.repository;

import com.api.ttoklip.domain.town.cart.itemUrl.entity.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUrlRepository extends JpaRepository<ItemUrl, Long> {

    void deleteAllByCartId(final Long cartId);
}
