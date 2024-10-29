package com.api.ttoklip.domain.town.cart.repository;

import com.api.ttoklip.domain.town.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
}
