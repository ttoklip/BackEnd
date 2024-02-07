package com.api.ttoklip.domain.question.comment.controller;

import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.comment.dto.request.CommentEditRequest;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.main.constant.QuestionResponseConstant;
import com.api.ttoklip.domain.question.comment.service.QuestionCommentService;
import com.api.ttoklip.global.success.Message;
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
@RequestMapping("/api/v1/question/comment")
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
                                    value = QuestionResponseConstant.CREATE_QUESTION_COMMENT,
                                    description = "댓글이 생성되었습니다."
                            )))})
    @PostMapping("/{postId}")
    public SuccessResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreateRequest request) {
        Message message = questionCommentService.register(postId, request);
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
                                    value = QuestionResponseConstant.REPORT_QUESTION_COMMENT,
                                    description = "댓글이 생성되었습니다."
                            )))})
    @PostMapping("/report/{commentId}")
    public SuccessResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = questionCommentService.report(commentId, request);
        return new SuccessResponse<>(message);
    }


    /* UPDATE - 댓글 수정 미사용 */
//    @Operation(summary = "댓글 수정", description = "지정된 게시글에 댓글을 수정합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "질문 수정 성공",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = SuccessResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = QuestionResponseConstant.EDIT_QUESTION_COMMENT,
//                                    description = "댓글이 수정되었습니다."
//                            )))})
//    @PatchMapping("/{commentId}")
//    public SuccessResponse<Message> edit(final @PathVariable Long commentId,
//                                         final @RequestBody CommentEditRequest request) {
//        Message message = questionCommentService.edit(commentId, request);
//        return new SuccessResponse<>(message);
//    }


    /* DELETE */
    @Operation(summary = "댓글 삭제", description = "지정된 게시글에 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.DELETE_QUESTION_COMMENT,
                                    description = "댓글이 삭제되었습니다."
                            )))})
    @DeleteMapping("/{commentId}")
    public SuccessResponse<Message> delete(final @PathVariable Long commentId) {
        Message message = questionCommentService.delete(commentId);
        return new SuccessResponse<>(message);
    }

}