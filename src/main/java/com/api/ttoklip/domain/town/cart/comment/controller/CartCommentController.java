package com.api.ttoklip.domain.town.cart.comment.controller;

import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.town.cart.comment.service.CartCommentService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Town", description = "우리동네 - 함께해요 댓글 API 입니다.")
@RestController
@RequestMapping("api/v1/town/carts/comment")
@RequiredArgsConstructor
public class CartCommentController {

    private final CartCommentService cartCommentService;

    // 함께해요(cart) 댓글

    /* CREATE */
    @Operation(summary = "함께해요 댓글 생성", description = "함께해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/{postId}")
    public SuccessResponse<Long> register(final @PathVariable Long postId,
                                          final @RequestBody CommentCreateRequest request) {
        Long createdCommentId = cartCommentService.register(postId, request);
        return new SuccessResponse<>(createdCommentId);
    }

    /* REPORT */
    @Operation(summary = "함께해요 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/report/{commentId}")
    public SuccessResponse<Long> report(final @PathVariable Long commentId,
                                        final @RequestBody ReportCreateRequest request) {
        cartCommentService.report(commentId, request);
        return new SuccessResponse<>(commentId);
    }

    /* UPDATE */
    @Operation(summary = "함께해요 댓글 수정", description = "함께해요 게시글 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 댓글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
    })
    @PatchMapping("/{commentId}")
    public SuccessResponse<Long> edit(final @PathVariable Long commentId,
                                      final @RequestBody CommentEditRequest request) {
        cartCommentService.edit(commentId, request);
        return new SuccessResponse<>(commentId);
    }

    /* DELETE */
    @Operation(summary = "댓글 삭제", description = "지정된 게시글에 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @DeleteMapping("/{commentId}")
    public SuccessResponse<Long> delete ( final @PathVariable Long commentId){
        cartCommentService.delete(commentId);
        return new SuccessResponse<>(commentId);
    }
}
