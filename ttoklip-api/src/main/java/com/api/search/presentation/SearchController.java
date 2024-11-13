package com.api.search.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.success.SuccessResponse;
import com.api.search.application.SearchFacade;
import com.api.search.presentation.response.HoneyTipPaging;
import com.api.search.presentation.response.NewsletterPaging;
import com.api.search.presentation.response.SearchResponseConstant;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search", description = "꿀팁공유해요, 뉴스레터, 우리동네(함께해요, 소통해요) 검색 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수
    private final SearchFacade searchFacade;

    @Operation(summary = "검색 기능 중 꿀팁 공유해요 api", description = "꿀팁 공유해요 게시판에 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 공유해요 검색 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SearchResponseConstant.HONEY_TIP,
                                    description = "꿀팁공유해요에 검색했습니다."
                            )))})
    @GetMapping("/honeytip")
    public SuccessResponse<HoneyTipPaging> searchHoneyTip(
            @Parameter(description = "포함될 꿀팁공유해요 키워드", required = true, example = "최신 팁")
            @RequestParam final String title,

            @Parameter(description = "인기순 or 최신순", example = "popularity or latest")
            @RequestParam final String sort,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        HoneyTipPaging honeyTipPaging = searchFacade.honeyTipSearch(title, pageable, sort);
        return new SuccessResponse<>(honeyTipPaging);
    }

    @Operation(summary = "검색 기능 중 뉴스레터 api", description = "뉴스레터 게시판에 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 검색 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SearchResponseConstant.NEWSLETTER,
                                    description = "뉴스레터에 검색했습니다."
                            )))})
    @GetMapping("/newsletter")
    public SuccessResponse<NewsletterPaging> searchNewsletter(
            @Parameter(description = "포함될 뉴스레터의 키워드", required = true, example = "최신 팁")
            @RequestParam final String title,

            @Parameter(description = "인기순 or 최신순", example = "popularity or latest")
            @RequestParam final String sort,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        NewsletterPaging newsletterPaging = searchFacade.newsletterPaging(title, pageable, sort);
        return new SuccessResponse<>(newsletterPaging);
    }

    @Operation(summary = "검색 기능 중 우리동네(소통해요) api", description = "소통해요 게시판에 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 검색 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    description = "우리동네(소통해요)에 검색했습니다."
                            )))})
    @GetMapping("/community")
    public SuccessResponse<CommunityPaging> searchCommunity(
            @Parameter(description = "포함될 우리동네(소통해요)의 키워드", required = true, example = "오늘")
            @RequestParam final String title,

            @Parameter(description = "인기순 or 최신순", example = "popularity or latest")
            @RequestParam final String sort,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CommunityPaging communityPaging = searchFacade.communityPaging(title, pageable, sort);
        return new SuccessResponse<>(communityPaging);
    }

    @Operation(summary = "검색 기능 중 우리동네(함께해요) api", description = "함께해요 게시판에 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 검색 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    description = "우리동네(함께해요)에 검색했습니다."
                            )))})
    @GetMapping("/cart")
    public SuccessResponse<CartPaging> searchCart(
            @Parameter(description = "포함될 우리동네(함께해요)의 키워드", required = true, example = "구매")
            @RequestParam final String title,

            @Parameter(description = "인기순 or 최신순", example = "popularity or latest")
            @RequestParam final String sort,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CartPaging cartPaging = searchFacade.cartPaging(title, pageable, sort);
        return new SuccessResponse<>(cartPaging);
    }
}
