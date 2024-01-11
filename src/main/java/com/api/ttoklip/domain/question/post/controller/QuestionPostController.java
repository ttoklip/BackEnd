package com.api.ttoklip.domain.question.post.controller;

import com.api.ttoklip.domain.question.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.service.QuestionPostService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question/post")
@RequiredArgsConstructor
public class QuestionPostController {

    private final QuestionPostService questionPostService;

    @Operation(summary = "새로운 질문 생성", description = "form/data로 새로운 질문을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "질문 생성 성공",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SuccessResponse.class)
    ))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Long> register(final @Validated @ModelAttribute QuestionCreateRequest request) {
        Long questionId = questionPostService.register(request);
        return new SuccessResponse<>(questionId);
    }

}
