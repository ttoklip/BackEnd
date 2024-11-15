package com.domain.cart.application;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.ItemUrl;
import com.domain.cart.domain.ItemUrlRepository;
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
