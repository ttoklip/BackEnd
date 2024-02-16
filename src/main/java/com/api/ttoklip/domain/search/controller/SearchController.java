package com.api.ttoklip.domain.search.controller;

import com.api.ttoklip.domain.search.constant.SearchResponseConstant;
import com.api.ttoklip.domain.search.response.CommunityPaging;
import com.api.ttoklip.domain.search.response.HoneyTipPaging;
import com.api.ttoklip.domain.search.response.NewsletterPaging;
import com.api.ttoklip.domain.search.service.SearchService;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search", description = "꿀팁공유해요, 뉴스레터, 우리동네(소통해요) 검색 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수
    private final SearchService searchService;

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
        HoneyTipPaging honeyTipPaging = searchService.honeyTipSearch(title, pageable, sort);
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
        NewsletterPaging newsletterPaging = searchService.newsletterPaging(title, pageable, sort);
        return new SuccessResponse<>(newsletterPaging);
    }

    @Operation(summary = "검색 기능 중 우리동네(소통해요) api", description = "우리동네 게시판에 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "우리동네 검색 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    description = "우리동네(소통해요)에 검색했습니다."
                            )))})
    @GetMapping("/our-town")
    public SuccessResponse<CommunityPaging> searchCommunity(
            @Parameter(description = "포함될 우리동네(소통해요)의 키워드", required = true, example = "오늘")
            @RequestParam final String title,

            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        CommunityPaging communityPaging = searchService.communityPaging(title, pageable);
        return new SuccessResponse<>(communityPaging);
    }
}
