package com.api.ttoklip.domain.town.cart.dto.response;

import com.api.ttoklip.domain.town.comment.dto.response.CartCommentResponse;
import com.api.ttoklip.domain.town.image.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CartResponse {

    private Long cartId;
    private String title;
    private String content;
    private String nickname;
    private String location;
    private String createdBy;

    private String chatLink;
    private String itemLink;

    private Long joinMember;

    private int totalPrice;
    private int personPrice;

    private List<ImageResponse> imageUrls;

    private List<CartCommentResponse> cartCommentResponses;
}
