package com.api.town.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import com.api.town.application.TownFacade;
import com.api.town.application.TownMainResponse;
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
@RequestMapping("/api/v1/town/main")
public class TownController {

    private final static int PAGE_SIZE = 10;

    private final TownFacade townFacade;

    /* Town Paging */
    @Operation(summary = "우리동네 메인", description = "함께해요, 소통해요 최신글 3개씩 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "우리동네 불러오기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = TownResponseConstant.getRecent3,
                                    description = "우리동네 메인 페이지입니다."
                            )))})
    @GetMapping
    public SuccessResponse<TownMainResponse> getCarts(
            @Parameter(description = "페이지 번호 (기본값 CITY)", example = "CITY, DISTRICT, TOWN")
            @RequestParam(required = false, defaultValue = "CITY") final String criteria
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        TownMainResponse cartMainResponse = townFacade.getRecent3(criteria, currentMemberId);
        return new SuccessResponse<>(cartMainResponse);
    }

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
            @Parameter(description = "페이지 번호 (기본값 CITY)", example = "CITY, DISTRICT, TOWN")
            @RequestParam(required = false, defaultValue = "CITY") final String criteria,
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new SuccessResponse<>(townFacade.getCommunities(criteria, pageable, currentMemberId));
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
            @RequestParam(required = false, defaultValue = "0") final int page,
            @Parameter(description = "시작가격", example = "30000")
            @RequestParam(required = false, defaultValue = "100") final Long startMoney,
            @Parameter(description = "마지막가격", example = "50000")
            @RequestParam(required = false, defaultValue = "100000000") final Long lastMoney,
            @Parameter(description = "시작인원", example = "1")
            @RequestParam(required = false, defaultValue = "1") final Long startParty,
            @Parameter(description = "마지막인원", example = "5000")
            @RequestParam(required = false, defaultValue = "500000") final Long lastParty,
            @Parameter(description = "페이지 번호 (기본값 CITY)", example = "CITY, DISTRICT, TOWN")
            @RequestParam(required = false, defaultValue = "CITY") final String criteria
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new SuccessResponse<>(
                townFacade.getCarts(pageable, startMoney, lastMoney, startParty, lastParty, criteria, currentMemberId)
        );
    }

}
