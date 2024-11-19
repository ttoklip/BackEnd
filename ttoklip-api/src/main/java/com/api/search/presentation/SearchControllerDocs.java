package com.api.search.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.TtoklipResponse;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Search", description = "꿀팁공유해요, 뉴스레터, 우리동네(함께해요, 소통해요) 검색 API")
@RequestMapping("/api/v1/search")
public interface SearchControllerDocs {

    @Operation(summary = "꿀팁 공유해요 검색", description = "꿀팁 공유해요 게시판에서 키워드로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 공유해요 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "SearchResponseConstant.HONEY_TIP",
                                    description = "꿀팁 공유해요에 검색했습니다."
                            )))})

    @GetMapping("/honeytip")
    TtoklipResponse<HoneyTipPaging> searchHoneyTip(
            @Parameter(description = "검색할 키워드", required = true, example = "최신 팁")
            @RequestParam String title,

            @Parameter(description = "정렬 방식 (인기순 or 최신순)", example = "popularity or latest")
            @RequestParam String sort,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page);

    @Operation(summary = "뉴스레터 검색", description = "뉴스레터 게시판에서 키워드로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "SearchResponseConstant.NEWSLETTER",
                                    description = "뉴스레터에 검색했습니다."
                            )))})

    @GetMapping("/newsletter")
    TtoklipResponse<NewsletterPaging> searchNewsletter(
            @Parameter(description = "검색할 키워드", required = true, example = "최신 팁")
            @RequestParam String title,

            @Parameter(description = "정렬 방식 (인기순 or 최신순)", example = "popularity or latest")
            @RequestParam String sort,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page);

    @Operation(summary = "우리동네(소통해요) 검색", description = "소통해요 게시판에서 키워드로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    description = "우리동네(소통해요)에 검색했습니다."
                            )))})

    @GetMapping("/community")
    TtoklipResponse<CommunityPaging> searchCommunity(
            @Parameter(description = "검색할 키워드", required = true, example = "오늘")
            @RequestParam String title,

            @Parameter(description = "정렬 방식 (인기순 or 최신순)", example = "popularity or latest")
            @RequestParam String sort,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page);

    @Operation(summary = "우리동네(함께해요) 검색", description = "함께해요 게시판에서 키워드로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 검색 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    description = "우리동네(함께해요)에 검색했습니다."
                            )))})

    @GetMapping("/cart")
    TtoklipResponse<CartPaging> searchCart(
            @Parameter(description = "검색할 키워드", required = true, example = "구매")
            @RequestParam String title,

            @Parameter(description = "정렬 방식 (인기순 or 최신순)", example = "popularity or latest")
            @RequestParam String sort,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page);
}
