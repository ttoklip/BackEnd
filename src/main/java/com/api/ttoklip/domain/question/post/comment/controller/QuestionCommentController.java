package com.api.ttoklip.domain.question.post.comment.controller;

import com.api.ttoklip.domain.question.main.constant.QuestionResponseConstant;
import com.api.ttoklip.domain.question.post.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.question.post.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.question.post.comment.service.QuestionCommentService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionCommentController {

    private final QuestionCommentService questionCommentService;

    /* CREATE */
    @Operation(summary = "새로운 댓글 생성", description = "지정된 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "질문 생성 성공",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = SuccessResponse.class),
            examples = @ExampleObject(
                    name = "SuccessResponse",
                    value = QuestionResponseConstant.createAndDeleteQuestion,
                    description = "댓글이 생성되었습니다."
    )))})
    @PostMapping("/comment/{postId}")
    public SuccessResponse<Long> register(final @PathVariable Long postId, final @RequestBody CommentCreateRequest commentCreateRequest) {
        Long createdCommentId = questionCommentService.register(postId, commentCreateRequest);
        return new SuccessResponse<>(createdCommentId);
    }


    /* UPDATE */
    @Operation(summary = "댓글 수정", description = "지정된 게시글에 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.createAndDeleteQuestion,
                                    description = "댓글이 수정되었습니다."
                            )))})
    @PatchMapping("comment/{commentId}")
    public SuccessResponse<Long> edit(final @PathVariable Long commentId, final @RequestBody CommentEditRequest commentEditRequest) {
        questionCommentService.edit(commentId, commentEditRequest);
        return new SuccessResponse<>(commentId);
    }


    /* DELETE */
    @Operation(summary = "댓글 삭제", description = "지정된 게시글에 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.createAndDeleteQuestion,
                                    description = "댓글이 삭제되었습니다."
                            )))})
    @DeleteMapping("comment/{commentId}")
    public SuccessResponse<Long> delete(final @PathVariable Long commentId) {
        questionCommentService.delete(commentId);
        return new SuccessResponse<>(commentId);
    }

}
