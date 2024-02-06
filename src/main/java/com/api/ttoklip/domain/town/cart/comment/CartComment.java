package com.api.ttoklip.domain.town.cart.comment;

import com.api.ttoklip.domain.common.comment.Comment;
import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(value = "Cart")
public class CartComment extends Comment {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Builder
    private CartComment(String content, Comment parent, Cart cart) {
        super(content, parent); // Comment 클래스의 생성자 호출
        this.cart = cart;
    }

    public static CartComment withParentOf(final CommentCreateRequest request, final Comment parent,
                                                final Cart cart) {
        return CartComment.builder()
                .content(request.getComment())
                .parent(parent)
                .cart(cart)
                .build();
    }

    public static CartComment orphanageOf(final CommentCreateRequest request, final Cart cart) {
        return CartComment.builder()
                .content(request.getComment())
                .parent(null)
                .cart(cart)
                .build();
    }
}
