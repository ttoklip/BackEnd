package com.api.ttoklip.domain.town.cart.repository;

import com.api.ttoklip.domain.town.cart.domain.CartImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartImageRepository extends JpaRepository<CartImage, Long> {
    void deleteAllByCartId(final Long cartId);
}
