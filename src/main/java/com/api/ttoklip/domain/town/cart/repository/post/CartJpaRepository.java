package com.api.ttoklip.domain.town.cart.repository.post;

import com.api.ttoklip.domain.town.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {
}
