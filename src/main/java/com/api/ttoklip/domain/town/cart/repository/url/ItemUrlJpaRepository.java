package com.api.ttoklip.domain.town.cart.repository.url;

import com.api.ttoklip.domain.town.cart.domain.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUrlJpaRepository extends JpaRepository<ItemUrl, Long> {
    void deleteAllByCartId(Long cartId);
}
