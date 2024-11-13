package com.api.cart.presentation;

import com.api.cart.application.CartCommentFacade;
import com.api.common.ReportWebCreate;
import com.api.global.success.Message;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import com.domain.common.comment.domain.CommentCreate;
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

    private final CartCommentFacade cartCommentFacade;

    // 함께해요(cart) 댓글

    /* CREATE */
    @Operation(summary = "함께해요 댓글 생성", description = "함께해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 댓글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/{postId}")
    public SuccessResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartCommentFacade.register(postId, request, currentMemberId);
        return new SuccessResponse<>(message);
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
    public SuccessResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportWebCreate request) {
        Message message = cartCommentFacade.report(commentId, request);
        return new SuccessResponse<>(message);
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
    public SuccessResponse<Message> delete(final @PathVariable Long commentId) {
        Message message = cartCommentFacade.delete(commentId);
        return new SuccessResponse<>(message);
    }
}
