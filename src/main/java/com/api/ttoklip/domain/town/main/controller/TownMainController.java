package com.api.ttoklip.domain.town.main.controller;

import com.api.ttoklip.domain.town.cart.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.dto.response.CartListResponse;
import com.api.ttoklip.domain.town.cart.dto.response.CartResponse;
import com.api.ttoklip.domain.town.cart.dto.response.CartSummaryResponse;
import com.api.ttoklip.domain.town.cart.service.CartService;
import com.api.ttoklip.domain.town.community.dto.request.CommCreateRequest;
import com.api.ttoklip.domain.town.community.dto.request.CommSearchCondition;
import com.api.ttoklip.domain.town.community.dto.response.CommListResponse;
import com.api.ttoklip.domain.town.community.dto.response.CommResponse;
import com.api.ttoklip.domain.town.community.service.CommService;
import com.api.ttoklip.domain.town.main.constant.TownResponseConstant;
import com.api.ttoklip.domain.town.main.dto.request.TownSearchCondition;
import com.api.ttoklip.domain.town.main.dto.response.TownSearchResponse;
import com.api.ttoklip.domain.town.main.service.TownMainService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/town/main")
public class TownMainController {

    private final CartService cartService;
    private final CommService commService;

    @Operation(summary = "함께해요 더보기 페이지 조회",
            description = "함께해요 더보기 페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 더보기 페이지 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartListResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.townValue
                            )))})
    @GetMapping("/carts")
    public SuccessResponse<List<CartSummaryResponse>> getCartPage() {

        List<CartSummaryResponse> cartListResponse = cartService.getAllCartsSummary();
        return new SuccessResponse<>(cartListResponse);
    }

    @Operation(summary = "소통해요 더보기 페이지 조회",
            description = "소통해요 더보기 페이지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 더보기 페이지 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommListResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.townValue
                            )))})
    @GetMapping("/comms")
    public SuccessResponse<CommListResponse> getCommPage(final @Validated @ModelAttribute CommSearchCondition condition,
                                                         final Pageable pageable) {
        CommListResponse commListResponse = commService.searchCommPaging(condition, pageable);
        return new SuccessResponse<>(commListResponse);
    }

    @Operation(summary = "함께해요 게시글 생성",
            description = "함께해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CartResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.townValue
                            )))})
    @PostMapping("/carts")
    public SuccessResponse<CartResponse> createCartPost(final @Validated @ModelAttribute CartCreateRequest request) {
        CartResponse cartResponse = cartService.createCartPost(request);
        return new SuccessResponse<>(cartResponse);
    }

    @Operation(summary = "소통해요 게시글 생성",
            description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = CommResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.townValue
                            )))})
    @PostMapping("/comms")
    public SuccessResponse<CommResponse> createCommPost(final @Validated @ModelAttribute CommCreateRequest request) {
        CommResponse commResponse = commService.createCommPost(request);
        return new SuccessResponse<>(commResponse);
    }
}
