package com.api.honeytip.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.comment.domain.CommentCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "HoneyTip Comment", description = "꿀팁공유해요 댓글 관리 API")
public interface HoneyTipCommentControllerDocs {

    @Operation(summary = "꿀팁공유해요 댓글 생성", description = "꿀팁공유해요 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁공유해요 댓글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.CREATE_HONEY_TIP_COMMENT",
                                    description = "댓글이 생성되었습니다."
                            )))} )
    TtoklipResponse<Message> register(@PathVariable Long postId, @RequestBody CommentCreate request);

    @Operation(summary = "꿀팁공유해요 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁공유해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.REPORT_HONEY_TIP_COMMENT",
                                    description = "댓글이 신고되었습니다."
                            )))} )
    TtoklipResponse<Message> report(@PathVariable Long commentId, @RequestBody ReportWebCreate request);

    @Operation(summary = "꿀팁공유해요 댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁공유해요 댓글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.DELETE_HONEY_TIP_COMMENT",
                                    description = "댓글이 삭제되었습니다."
                            )))} )
    TtoklipResponse<Message> delete(@PathVariable Long commentId);
}
