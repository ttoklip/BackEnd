package com.domain.cart.application;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.CartImage;
import com.domain.cart.domain.CartImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartImageService {

    private final CartImageRepository cartImageRepository;

    @Transactional
    public void register(final Cart cart, final String uploadUrl) {
        CartImage newCartImage = CartImage.of(cart, uploadUrl);
        cartImageRepository.save(newCartImage);
    }

    @Transactional
    public void deleteAllByPostId(final Long cartId) {
        cartImageRepository.deleteAllByCartId(cartId);
    }
}
