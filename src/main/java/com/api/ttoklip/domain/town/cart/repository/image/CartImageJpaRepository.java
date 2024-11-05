package com.api.ttoklip.domain.town.cart.repository.image;

import com.api.ttoklip.domain.town.cart.domain.CartImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartImageJpaRepository extends JpaRepository<CartImage, Long> {
    void deleteAllByCartId(Long cartId);

}
