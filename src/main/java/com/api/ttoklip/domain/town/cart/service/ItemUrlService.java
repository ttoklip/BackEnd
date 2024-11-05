package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.town.cart.domain.ItemUrl;
import com.api.ttoklip.domain.town.cart.repository.url.ItemUrlRepository;
import com.api.ttoklip.domain.town.cart.domain.Cart;
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
