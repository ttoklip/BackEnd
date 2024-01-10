package com.api.ttoklip.domain.question.controller;

import com.api.ttoklip.domain.question.dto.request.QuestionSearchCondition;
import com.api.ttoklip.domain.question.dto.response.QuestionSearchResponse;
import com.api.ttoklip.domain.question.service.QuestionService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "질문해요 검색 API",
            description = "지정된 조건에 따라 질문해요 게시판을 페이징하여 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 페이지 조회 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = QuestionSearchResponse.class)))
    })
    @GetMapping("/question")
    public SuccessResponse<QuestionSearchResponse> search(@ModelAttribute QuestionSearchCondition condition, Pageable pageable) {
        QuestionSearchResponse response = questionService.search(condition, pageable);
        return new SuccessResponse<>(response);
    }
}
