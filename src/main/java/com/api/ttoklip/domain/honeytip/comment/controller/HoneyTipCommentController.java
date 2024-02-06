package com.api.ttoklip.domain.honeytip.comment.controller;

import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.honeytip.comment.constant.HoneyTipResponseConstant;
import com.api.ttoklip.domain.honeytip.comment.service.HoneyTipCommentService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HoneyTip Comment", description = "HoneyTip Comment API")
@RestController
@RequestMapping("/api/v1/honeytip/comment")
@RequiredArgsConstructor
public class HoneyTipCommentController {

    private final HoneyTipCommentService honeyTipCommentService;

    /* CREATE */
    @Operation(summary = "새로운 댓글 생성", description = "지정된 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.CREATE_HONEY_TIP_COMMENT,
                                    description = "댓글이 생성되었습니다."
                            )))})
    @PostMapping("/{postId}")
    public SuccessResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreateRequest request) {
        Message message = honeyTipCommentService.register(postId, request);
        return new SuccessResponse<>(message);
    }

    /* REPORT */
    @Operation(summary = "질문 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.REPORT_HONEY_TIP_COMMENT,
                                    description = "댓글이 신고되었습니다."
                            )))})
    @PostMapping("/report/{commentId}")
    public SuccessResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = honeyTipCommentService.report(commentId, request);
        return new SuccessResponse<>(message);
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
                                    value = HoneyTipResponseConstant.DELETE_HONEY_TIP_COMMENT,
                                    description = "댓글이 삭제되었습니다."
                            )))})
    @DeleteMapping("/{commentId}")
    public SuccessResponse<Message> delete(final @PathVariable Long commentId) {
        Message message = honeyTipCommentService.delete(commentId);
        return new SuccessResponse<>(message);
    }
}
