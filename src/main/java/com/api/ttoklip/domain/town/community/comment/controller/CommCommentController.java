package com.api.ttoklip.domain.town.community.comment.controller;

import com.api.ttoklip.domain.town.community.comment.dto.request.CommCommentCreateRequest;
import com.api.ttoklip.domain.town.community.comment.dto.request.CommCommentUpdateRequest;
import com.api.ttoklip.domain.town.community.comment.service.CommCommentService;
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

@Tag(name = "Town", description = "우리동네 - 소통해요 댓글 API 입니다.")
@RestController
@RequestMapping("/town/comms")
@RequiredArgsConstructor
public class CommCommentController {

    private CommCommentService commCommentService;

    // 소통해요(comm) 댓글
    @Operation(summary = "소통해요 댓글 생성", description = "소통해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 댓글 생성 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 댓글 생성 실패"),
    })
    @PostMapping("comment/{commId}")
    public SuccessResponse<Long> createCommComment(final @PathVariable Long cartId,
                                                   final @RequestBody CommCommentCreateRequest commCommentCreateRequest) {
        Long createdCommentId = commCommentService.createCommComment(cartId, commCommentCreateRequest);
        return new SuccessResponse<>(createdCommentId);
    }

    @Operation(summary = "소통해요 댓글 수정", description = "소통해요 게시글 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 댓글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 댓글 수정 실패"),
    })
    @PatchMapping("comment/{commentId}")
    public SuccessResponse<Long> updateCommComment(final @PathVariable Long commentId,
                                                   final @RequestBody CommCommentUpdateRequest commCommentUpdateRequest) {
        CommCommentService.updateCommComment(commentId, commCommentUpdateRequest);
        return new SuccessResponse<>(commentId);
    }

    @Operation(summary = "소통해요 댓글 삭제", description = "소통해요 게시글 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 댓글 삭제 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "소통해요 댓글 삭제 실패"),
    })
    @DeleteMapping("comment/{commentId}")
    public SuccessResponse<Long> deletCommComment(final @PathVariable Long commentId) {
        CommCommentService.deletCommComment(commentId);
        return new SuccessResponse<>(commentId);
    }
}
