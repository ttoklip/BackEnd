package com.api.ttoklip.domain.town.main.controller;

import com.api.ttoklip.domain.town.cart.post.dto.request.CartCreateRequest;
import com.api.ttoklip.domain.town.cart.post.dto.response.CartSingleResponse;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import com.api.ttoklip.domain.town.community.post.dto.request.CommunityCreateRequest;
import com.api.ttoklip.domain.town.community.post.service.CommunityPostService;
import com.api.ttoklip.domain.town.main.constant.TownResponseConstant;
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
@RequestMapping("api/v1/town/main")
public class TownMainController {

    private final CartPostService cartPostService;
    private final CommunityPostService commService;

//    @Operation(summary = "함께해요 더보기 페이지 조회",
//            description = "함께해요 더보기 페이지를 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "함께해요 더보기 페이지 조회 성공",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = CartListResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = TownResponseConstant.townValue
//                            )))})
//    @GetMapping("/carts")
//    public SuccessResponse<List<CartSummaryResponse>> getCartPage() {
//
//        List<CartSummaryResponse> cartListResponse = cartPostService.getAllCartsSummary();
//        return new SuccessResponse<>(cartListResponse);
//    }

//    @Operation(summary = "소통해요 더보기 페이지 조회",
//            description = "소통해요 더보기 페이지를 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "소통해요 더보기 페이지 조회 성공",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = CommunityListResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = TownResponseConstant.townValue
//                            )))})
//    @GetMapping("/comms")
//    public SuccessResponse<CommunityListResponse> getCommPage(final @Validated @ModelAttribute CommunitySearchCondition condition,
//                                                              final Pageable pageable) {
//        CommunityListResponse commListResponse = commService.searchCommunityPaging(condition, pageable);
//        return new SuccessResponse<>(commListResponse);
//    }
//
//    @Operation(summary = "함께해요 게시글 생성",
//            description = "함께해요 게시글을 생성합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공",
//                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
//                            schema = @Schema(implementation = CartSingleResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = TownResponseConstant.townValue
//                            )))})
//    @PostMapping("/carts")
//    public SuccessResponse<CartSingleResponse> createCartPost(final @Validated @ModelAttribute CartCreateRequest request) {
//        CartSingleResponse cartSingleResponse = cartPostService.createCartPost(request);
//        return new SuccessResponse<>(cartSingleResponse);
//    }
//
//    @Operation(summary = "소통해요 게시글 생성",
//            description = "소통해요 게시글을 생성합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
//                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
//                            schema = @Schema(implementation = CommunityResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = TownResponseConstant.townValue
//                            )))})
//    @PostMapping("/comms")
//    public SuccessResponse<CommunityResponse> createCommPost(final @Validated @ModelAttribute CommunityCreateRequest request) {
//        CommunityResponse commResponse = commService.createCommunityPost(request);
//        return new SuccessResponse<>(commResponse);
//    }
}
