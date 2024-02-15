package com.api.ttoklip.domain.town.main.controller;

import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import com.api.ttoklip.domain.town.community.post.service.CommunityPostService;
import com.api.ttoklip.domain.town.main.service.TownMainService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Town Main", description = "Town Main API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/town/main")
public class TownMainController {

    private final CartPostService cartPostService;
    private final CommunityPostService communityPostService;
    private final TownMainService townMainService;

    // todo 최근 글 4개 페이징, 글쓰기 페이지로 이동(추가 안 해도 됨), 글목록 페이지로

    // 메인 화면 조회
//    @Operation(summary = "우리동네 메인화면 조회", description = "우리동네 메인 화면을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "우리동네 메인 화면 조회 성공",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = SuccessResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = TownResponseConstant.listTowns,
//                                    description = "우리동네 메인 화면이 조회되었습니다."
//                            )))})
//    @GetMapping
//    public SuccessResponse<TownMainResponse> d() {
//        return new SuccessResponse<>(TownMainService.main());
//    }




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
