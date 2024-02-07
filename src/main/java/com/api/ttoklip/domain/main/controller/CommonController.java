package com.api.ttoklip.domain.main.controller;

import com.api.ttoklip.domain.main.constant.QuestionResponseConstant;
import com.api.ttoklip.domain.main.dto.response.CommonDefaultResponse;
import com.api.ttoklip.domain.main.dto.response.CommonSearchResponse;
import com.api.ttoklip.domain.main.service.CommonService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Question & HoneyTip Main", description = "Question & HoneyTip Main API")
@RestController
@RequestMapping("/api/v1/common/main")
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @Operation(summary = "1번 질문해요 검색 API",
            description = "지정된 조건에 따라 질문해요 게시판을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 페이지 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.questionValue
                            )))})
    @GetMapping("/search")
    public SuccessResponse<CommonSearchResponse> search(@RequestParam String content, Pageable pageable) {
        CommonSearchResponse response = commonService.search(content, pageable);
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "3, 6번 인기 순위 TOP5 & 카테고리별 조회 API",
            description = "질문해요 메인페이지 도달시 인기 순위 TOP5와 카테고리별로 10개씩 한번에 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문해요 메인 페이지 인기 순위 TOP5와 카테고리별 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.categoryValue,
                                    description = "실제로는 인기순위 5개와 카테고리별로별로 10개씩 한번에 응답이 나갑니다."
                            )))})
    @GetMapping
    public SuccessResponse<CommonDefaultResponse> category() {
        CommonDefaultResponse defaultCategoryRead = commonService.getDefaultCategoryRead();
        return new SuccessResponse<>(defaultCategoryRead);
    }
}
