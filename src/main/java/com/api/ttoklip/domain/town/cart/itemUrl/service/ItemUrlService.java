package com.api.ttoklip.domain.town.cart.itemUrl.service;

import com.api.ttoklip.domain.town.cart.image.entity.CartImage;
import com.api.ttoklip.domain.town.cart.itemUrl.entity.ItemUrl;
import com.api.ttoklip.domain.town.cart.itemUrl.repository.ItemUrlRepository;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemUrlService {

    private final ItemUrlRepository itemUrlRepository;

    @Transactional
    public void register(final Cart cart, final String uploadUrl) {
        ItemUrl newItemUrl = ItemUrl.of(cart, uploadUrl);
        itemUrlRepository.save(newItemUrl);
    }

    @Transactional
    public void deleteAllByPostId(final Long cartId) {
        itemUrlRepository.deleteAllByCartId(cartId);
    }
}
