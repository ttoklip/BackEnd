package com.domain.cart.infrastructure;

import com.domain.cart.domain.CartImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartImageJpaRepository extends JpaRepository<CartImage, Long> {
    void deleteAllByCartId(Long cartId);

}
