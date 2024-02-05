package com.api.ttoklip.domain.town.cart.image.service;

import com.api.ttoklip.domain.town.cart.image.entity.CartImage;
import com.api.ttoklip.domain.town.cart.image.repository.CartImageRepository;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
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
}
