package com.api.ttoklip.domain.town.main.controller;

import com.api.ttoklip.domain.mypage.main.constant.MyPageConstant;
import com.api.ttoklip.domain.search.response.CartPaging;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.town.cart.post.service.CartPostService;
import com.api.ttoklip.domain.town.community.post.service.CommunityPostService;
import com.api.ttoklip.domain.town.main.constant.TownResponseConstant;
import com.api.ttoklip.domain.town.main.service.TownMainService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Town Main", description = "Town Main API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/town/main")
public class TownMainController {

    private final static int PAGE_SIZE = 10;

    private final CartPostService cartPostService;
    private final CommunityPostService communityPostService;
    private final TownMainService townMainService;

    // todo 최근 글 4개 페이징, 글쓰기 페이지로 이동(추가 안 해도 됨), 글목록 페이지로

    /* Cart Paging */
//    @Operation(summary = "함께해요 더보기", description = "함께해요 글 목록 불러오기")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "함께해요 불러오기 성공",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = SuccessResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = MyPageConstant.scrapHoneyTipsResponse,
//                                    description = "함께해요 글 목록을 불러왔습니다."
//                            )))})
//    @GetMapping("/cart")
//    public SuccessResponse<CartPaging> getCarts(
//            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
//            @RequestParam(required = false, defaultValue = "0") final int page) {
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//        return new SuccessResponse<>(cartPageService.getCarts(pageable));
//    }

    /* Community Paging */
    @Operation(summary = "소통해요 더보기", description = "소통해요 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 불러오기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.getCommunities,
                                    description = "소통해요 글 목록을 불러왔습니다."
                            )))})
    @GetMapping("/community")
    public SuccessResponse<CommunityPaging> getCommunities(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(townMainService.getCommunities(pageable));
    }

    /* Cart Paging */
    @Operation(summary = "함께해요 더보기", description = "함께해요 글 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 불러오기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.getCarts,
                                    description = "함께해요 글 목록을 불러왔습니다."
                            )))})
    @GetMapping("/cart")
    public SuccessResponse<CartPaging> getCarts(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return new SuccessResponse<>(townMainService.getCarts(pageable));
    }

}
