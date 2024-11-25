package com.api.town.presentation;

import com.api.cart.presentation.dto.response.CartPaging;
import com.api.community.presentation.dto.response.CommunityPaging;
import com.api.global.support.response.TtoklipResponse;
import com.api.town.application.TownMainResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Town Main", description = "우리동네 관리 API")
public interface TownControllerDocs {

    @Operation(summary = "우리동네 메인 조회", description = "함께해요, 소통해요 최신글 3개씩 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "우리동네 메인 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TownResponseConstant.getRecent3",
                                    description = "우리동네 메인 페이지입니다."
                            )))})
    TtoklipResponse<TownMainResponse> getCarts(
            @Parameter(description = "필터 기준 (기본값 CITY)", example = "CITY, DISTRICT, TOWN")
            @RequestParam(defaultValue = "CITY") String criteria);

    @Operation(summary = "소통해요 글 목록 조회", description = "소통해요 글 목록을 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TownResponseConstant.getCommunities",
                                    description = "소통해요 글 목록을 불러왔습니다."
                            )))})
    TtoklipResponse<CommunityPaging> getCommunities(
            @Parameter(description = "필터 기준 (기본값 CITY)", example = "CITY, DISTRICT, TOWN")
            @RequestParam(defaultValue = "CITY") String criteria,
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page);

    @Operation(summary = "함께해요 글 목록 조회", description = "함께해요 글 목록을 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "TownResponseConstant.getCarts",
                                    description = "함께해요 글 목록을 불러왔습니다."
                            )))})
    TtoklipResponse<CartPaging> getCarts(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "시작 가격", example = "30000")
            @RequestParam(defaultValue = "100") Long startMoney,
            @Parameter(description = "마지막 가격", example = "50000")
            @RequestParam(defaultValue = "100000000") Long lastMoney,
            @Parameter(description = "시작 인원", example = "1")
            @RequestParam(defaultValue = "1") Long startParty,
            @Parameter(description = "마지막 인원", example = "5000")
            @RequestParam(defaultValue = "500000") Long lastParty,
            @Parameter(description = "필터 기준 (기본값 CITY)", example = "CITY, DISTRICT, TOWN")
            @RequestParam(defaultValue = "CITY") String criteria);
}
