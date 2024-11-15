package com.domain.cart.application;

import com.domain.cart.domain.Cart;
import com.domain.cart.domain.vo.TradeStatus;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CartThumbnailResponse(
        Long id, String title, String location, Long totalPrice, Long partyMax, String writer,
        String writerProfileImageUrl, int partyCnt, int commentCount, int currentPrice, TradeStatus tradeStatus) {

    public static CartThumbnailResponse from(final Cart cart) {
        int currentPrice = (int) (cart.getTotalPrice() / cart.getPartyMax() * cart.getCartMembers().size());
        return builder()
                .id(cart.getId())
                .title(cart.getTitle())
                .location(cart.getLocation())
                .totalPrice(cart.getTotalPrice())
                .partyMax(cart.getPartyMax())
                .partyCnt(cart.getCartMembers().size())
                .currentPrice(currentPrice)
                .writer(cart.getMember().getNickname())
                .writerProfileImageUrl(cart.getMember().getProfile().getProfileImgUrl())
                .commentCount(cart.getCartComments().size())
                .tradeStatus(cart.getStatus())
                .build();
    }
}
