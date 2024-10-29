package com.api.ttoklip.domain.town.cart.repository;

import com.api.ttoklip.domain.town.cart.domain.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUrlRepository extends JpaRepository<ItemUrl, Long> {

    void deleteAllByCartId(final Long cartId);
}
