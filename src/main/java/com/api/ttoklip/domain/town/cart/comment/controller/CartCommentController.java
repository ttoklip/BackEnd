package com.api.ttoklip.domain.town.cart.comment.controller;

import com.api.ttoklip.domain.town.cart.comment.dto.request.CartCommentCreateRequest;
import com.api.ttoklip.domain.town.cart.comment.dto.request.CartCommentUpdateRequest;
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
@RequestMapping("/town/carts")
@RequiredArgsConstructor
public class CartCommentController {

    private final CartCommentService cartCommentService;

    // 함께해요(cart) 댓글
    @Operation(summary = "함께해요 댓글 생성", description = "함께해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 댓글 생성 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 댓글 생성 실패"),
    })
    @PostMapping("/comment/{cartId}")
    public SuccessResponse<Long> createCartComment(final @PathVariable Long cartId,
                                                   final @RequestBody CartCommentCreateRequest cartCommentCreateRequest) {
        Long createdCommentId = cartCommentService.createCartComment(cartId, cartCommentCreateRequest);
        return new SuccessResponse<>(createdCommentId);
    }

    @Operation(summary = "함께해요 댓글 수정", description = "함께해요 게시글 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 댓글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 댓글 수정 실패"),
    })
    @PatchMapping("/comment/{commentId}")
    public SuccessResponse<Long> updateCartComment(final @PathVariable Long commentID,
                                                   final @RequestBody CartCommentUpdateRequest cartCommentUpdateRequest) {
        CartCommentService.updateCartComment(commentID, cartCommentUpdateRequest);
        return new SuccessResponse<>(commentID);
    }

    @Operation(summary = "함께해요 댓글 삭제", description = "함께해요 게시글 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 댓글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "함께해요 댓글 삭제 실패"),
    })
    @DeleteMapping("/comment/{commentId}")
    public SuccessResponse<Long> deletCartComment(final @PathVariable Long commentId) {
        CartCommentService.deletCartComment(commentId);
        return new SuccessResponse<>(commentId);
    }
}
