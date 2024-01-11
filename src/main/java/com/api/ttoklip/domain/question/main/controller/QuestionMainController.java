package com.api.ttoklip.domain.question.main.controller;

import com.api.ttoklip.domain.question.main.constant.QuestionResponseConstant;
import com.api.ttoklip.domain.question.main.dto.request.QuestionSearchCondition;
import com.api.ttoklip.domain.question.main.dto.response.QuestionCategoryResponse;
import com.api.ttoklip.domain.question.main.dto.response.QuestionSearchResponse;
import com.api.ttoklip.domain.question.main.service.QuestionMainService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question/main")
@RequiredArgsConstructor
public class QuestionMainController {

    private final QuestionMainService questionMainService;

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
    public SuccessResponse<QuestionSearchResponse> search(@ModelAttribute QuestionSearchCondition condition, Pageable pageable) {
        QuestionSearchResponse response = questionMainService.search(condition, pageable);
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "6번 카테고리별 조회 API",
            description = "질문해요 메인페이지 도달시 한번에 카테고리별로 4개씩 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문해요 메인 페이지 카테고리별 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = QuestionCategoryResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.categoryValue,
                                    description = "실제로는 카테고리별로 4개씩 응답이 나갑니다."
    )))})
    @GetMapping
    public SuccessResponse<QuestionCategoryResponse> category() {
        QuestionCategoryResponse response = questionMainService.category();
        return new SuccessResponse<>(response);
    }
}
