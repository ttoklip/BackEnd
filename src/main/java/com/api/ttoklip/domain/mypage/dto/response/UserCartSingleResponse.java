package com.api.ttoklip.domain.mypage.dto.response;

import com.api.ttoklip.domain.town.cart.post.entity.Cart;
import com.api.ttoklip.domain.town.cart.post.entity.TradeStatus;
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
    private int currentPrice;
    private TradeStatus tradeStatus;

    public static UserCartSingleResponse cartFrom(final Cart cart) {
        int currentPrice = (int) (cart.getTotalPrice() / cart.getPartyMax() * cart.getCartMembers().size());
        return UserCartSingleResponse.builder()
                .id(cart.getId())
                .title(cart.getTitle())
                .location(cart.getLocation())
                .totalPrice(cart.getTotalPrice())
                .partyMax(cart.getPartyMax())
                .partyCnt(cart.getCartMembers().size())
                .currentPrice(currentPrice)
                .writer(cart.getMember().getNickname())
                .commentCount(cart.getCartComments().size())
                .tradeStatus(cart.getStatus())
                .build();
    }
}
