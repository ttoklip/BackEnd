package com.domain.cart.infrastructure;

import com.domain.cart.domain.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemUrlJpaRepository extends JpaRepository<ItemUrl, Long> {
    void deleteAllByCartId(Long cartId);
}
