package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.town.cart.domain.CartImage;
import com.api.ttoklip.domain.town.cart.repository.image.CartImageRepository;
import com.api.ttoklip.domain.town.cart.domain.Cart;
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
