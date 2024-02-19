package com.api.ttoklip.domain.mypage.main.dto.response;

import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCartSingleResponse {
    private Long id;
    private String title;
    private String location;
    private Long totalPrice;
    private Long partyMax;
    private String writer;
    private int partyCnt;
    private int commentCount;

    public static UserCartSingleResponse of (final Cart cart, final CartSingleResponse cartSingleResponse){
        return UserCartSingleResponse.builder()
                .id(cart.getId())
                .title(cart.getTitle())
                .location(cart.getLocation())
                .totalPrice(cart.getTotalPrice())
                .partyMax(cart.getPartyMax())
                .partyCnt(cartSingleResponse.getPartyCnt())
                .writer(cartSingleResponse.getWriter())
                .commentCount(cart.getCartComments().size())
                .build();
    }
}
