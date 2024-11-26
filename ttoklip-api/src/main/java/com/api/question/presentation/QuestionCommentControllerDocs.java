package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.comment.domain.CommentCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Question Comment", description = "질문 댓글 관리 API")
public interface QuestionCommentControllerDocs {

    @Operation(summary = "질문 댓글 생성", description = "질문 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 댓글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.CREATE_QUESTION_COMMENT",
                                    description = "댓글이 생성되었습니다."
                            )))})
    TtoklipResponse<Message> register(@PathVariable Long postId, @RequestBody CommentCreate request);

    @Operation(summary = "질문 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 댓글 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.REPORT_QUESTION_COMMENT",
                                    description = "댓글이 신고되었습니다."
                            )))})
    TtoklipResponse<Message> report(@PathVariable Long commentId, @RequestBody ReportWebCreate request);

    @Operation(summary = "질문 댓글 삭제", description = "질문 게시글에 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 댓글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.DELETE_QUESTION_COMMENT",
                                    description = "댓글이 삭제되었습니다."
                            )))})
    TtoklipResponse<Message> delete(@PathVariable Long commentId);

    @Operation(summary = "질문 댓글 좋아요 추가", description = "질문 ID에 해당하는 댓글에 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.REGISTER_LIKE",
                                    description = "질문 댓글의 좋아요를 추가했습니다."
                            )))})
    TtoklipResponse<Message> registerLike(@PathVariable Long commentId);

    @Operation(summary = "질문 댓글 좋아요 취소", description = "질문 ID에 해당하는 댓글에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "QuestionResponseConstant.CANCEL_LIKE",
                                    description = "질문 댓글의 좋아요를 취소했습니다."
                            )))})
    TtoklipResponse<Message> cancelLike(@PathVariable Long commentId);
}
