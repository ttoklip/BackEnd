package com.api.ttoklip.domain.town.main.repository;

import com.api.ttoklip.domain.town.cart.domain.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartPageRepository extends JpaRepository<Cart, Long> {

    Page<Cart> findAllByOrderByIdDesc(Pageable pageable);
}
