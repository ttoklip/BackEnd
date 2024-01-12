package com.api.ttoklip.domain.question.post.post.controller;

import com.api.ttoklip.domain.question.main.constant.QuestionResponseConstant;
import com.api.ttoklip.domain.question.post.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.question.post.post.dto.request.QuestionCreateRequest;
import com.api.ttoklip.domain.question.post.post.dto.request.QuestionEditRequest;
import com.api.ttoklip.domain.question.post.post.dto.response.QuestionWithCommentResponse;
import com.api.ttoklip.domain.question.post.post.service.QuestionPostService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                schema = @Schema(implementation = SuccessResponse.class),
                examples = @ExampleObject(
                        name = "SuccessResponse",
                        value = QuestionResponseConstant.createAndDeleteQuestion,
                        description = "질문이 생성되었습니다."
    )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Long> register(final @Validated @ModelAttribute QuestionCreateRequest request) {
        Long questionId = questionPostService.register(request);
        return new SuccessResponse<>(questionId);
    }


    @Operation(summary = "질문 게시글 삭제", description = "질문 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "질문 삭제 성공",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = SuccessResponse.class),
                examples = @ExampleObject(
                        name = "SuccessResponse",
                        value = QuestionResponseConstant.createAndDeleteQuestion,
                        description = "질문이 삭제되었습니다."
    )))})
    @DeleteMapping("/{postId}")
    public SuccessResponse<Long> delete(final @PathVariable Long postId) {
        questionPostService.delete(postId);
        return new SuccessResponse<>(postId);
    }

    /* READ */
    @Operation(summary = "질문 게시글 조회", description = "질문 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "질문 조회 성공",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = SuccessResponse.class),
                examples = @ExampleObject(
                        name = "SuccessResponse",
                        value = QuestionResponseConstant.readSingleQuestion,
                        description = "질문이 조회되었습니다."
    )))})
    @GetMapping("/{postId}")
    public SuccessResponse<QuestionWithCommentResponse> getSinglePost(final @PathVariable Long postId) {
        QuestionWithCommentResponse response = questionPostService.getSinglePost(postId);
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "질문 게시글 수정", description = "질문 ID에 해당하는 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.createAndDeleteQuestion,
                                    description = "질문이 수정되었습니다."
                            )))})
    @PatchMapping("/{postId}")
    public SuccessResponse<Long> edit(final @PathVariable Long postId, final @RequestBody QuestionEditRequest request) {
        questionPostService.edit(postId, request);
        return new SuccessResponse<>(postId);
    }
}
