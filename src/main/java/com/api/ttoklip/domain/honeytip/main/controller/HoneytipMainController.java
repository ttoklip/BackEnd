package com.api.ttoklip.domain.honeytip.main.controller;

import com.api.ttoklip.domain.honeytip.main.constant.HoneytipResponseConstant;
import com.api.ttoklip.domain.honeytip.main.dto.response.HoneytipMainRes;
import com.api.ttoklip.domain.honeytip.main.service.HoneytipMainService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Honeytip Main", description = "Honeytip Main API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/honeytips/main")
public class HoneytipMainController {

    private final HoneytipMainService honeytipMainService;

    @Operation(summary = "꿀팁 공유해요 Top5 & 카테고리별 조회 API",
            description = "오늘의 인기 꿀팁 Top5 + 꿀팁 공유해요 데이터를 함께 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "오늘의 인기 꿀팁 Top5와 카테고리별 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneytipResponseConstant.honeytipValue,
                                    description = "인기 꿀팁 Top 5와 카테고리별 3개가 한번에 응답으로 나갑니다."
    )))})
    @GetMapping
    public SuccessResponse<HoneytipMainRes> category() {
        return new SuccessResponse<>(honeytipMainService.main());
    }
}
