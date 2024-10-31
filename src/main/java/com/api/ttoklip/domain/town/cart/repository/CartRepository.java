package com.api.ttoklip.domain.town.cart.repository;

import com.api.ttoklip.domain.town.TownCriteria;
import com.api.ttoklip.domain.town.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {

}
