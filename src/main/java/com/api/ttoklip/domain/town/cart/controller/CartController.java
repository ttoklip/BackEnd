package com.api.ttoklip.domain.town.cart.controller;

import com.api.ttoklip.domain.town.cart.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.dto.request.CartSearchCondition;
import com.api.ttoklip.domain.town.cart.dto.request.CartUpdateRequest;
import com.api.ttoklip.domain.town.cart.dto.response.CartListResponse;
import com.api.ttoklip.domain.town.cart.dto.response.CartResponse;
import com.api.ttoklip.domain.town.cart.service.CartService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@Tag(name = "Town", description = "우리동네 - 함께해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/town")
public class CartController {

    private final CartService cartService;

    // 함께해요 - cart
    @Operation(summary = "함께해요 더보기 페이지 조회",
            description = "함께해요 더보기 페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 더보기 페이지 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartListResponse.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 더보기 페이지 조회 실패"),
    })
    @GetMapping("/carts")
    public SuccessResponse<CartListResponse> getCartPage(final @Validated @ModelAttribute CartSearchCondition condition,
                                                         final Pageable pageable) {
        CartListResponse cartListResponse = cartService.searchCartPaging(condition, pageable);
        return new SuccessResponse<>(cartListResponse);
    }

    @Operation(summary = "함께해요 게시글 조회",
            description = "함께해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 게시글 조회 실패"),
    })
    @GetMapping("/carts/{cartId}")
    public SuccessResponse<CartResponse> getCart(final @PathVariable Long cartId) {
        CartResponse cartResponse = cartService.getCart(cartId);
        return new SuccessResponse<>(cartResponse);
    }

    @Operation(summary = "함께해요 게시글 생성",
            description = "함께해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 게시글 생성 실패"),
    })
    @PostMapping("/carts")
    public SuccessResponse<CartResponse> createCartPost(final @Validated @ModelAttribute CartCreateRequest request) {
        CartResponse cartResponse = cartService.createCartPost(request);
        return new SuccessResponse<>(cartResponse);
    }

    // 함께해요는 글 삭제 불가능, 수정만 ok
    @Operation(summary = "함께해요 게시글 수정",
            description = "함께해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 게시글 수정 실패"),
    })
    @PatchMapping("/carts/{cartId}")
    public SuccessResponse<Long> updateCartPost(final @PathVariable Long cartId,
                                                final @ModelAttribute CartUpdateRequest request) {
        cartService.updateCartPost(cartId, request);
        return new SuccessResponse<>(cartId);
    }
}
