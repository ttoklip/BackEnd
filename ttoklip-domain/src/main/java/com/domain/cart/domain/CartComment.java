package com.domain.cart.domain;

import com.domain.common.comment.domain.Comment;
import com.domain.common.comment.domain.CommentCreate;
import com.domain.member.domain.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private CartComment(String content, Comment parent, Cart cart, Member member) {
        super(content, parent, member); // Comment 클래스의 생성자 호출
        this.cart = cart;
    }

    public static CartComment withParentOf(final CommentCreate create, final Comment parent,
                                           final Cart cart, final Member member) {
        return CartComment.builder()
                .content(create.getComment())
                .parent(parent)
                .cart(cart)
                .member(member)
                .build();
    }

    public static CartComment orphanageOf(final CommentCreate create, final Cart cart, final Member member) {
        return CartComment.builder()
                .content(create.getComment())
                .parent(null)
                .cart(cart)
                .member(member)
                .build();
    }
}
