package com.api.ttoklip.domain.town.cart.service;

import com.api.ttoklip.domain.town.cart.domain.CartComment;
import com.api.ttoklip.domain.town.cart.repository.post.CartRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartCommentService {

    private final CartRepository cartRepository;

    public List<CartComment> findActiveCommentsByCartId(Long postId) {
        return cartRepository.findActiveCommentsByCartId(postId);
    }

}
