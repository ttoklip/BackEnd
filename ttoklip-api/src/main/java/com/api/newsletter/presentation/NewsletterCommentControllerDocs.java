package com.api.newsletter.presentation;

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

@Tag(name = "Newsletter Comment", description = "뉴스레터 댓글 관리 API")
@RequestMapping("/api/v1/newsletter/comment")
public interface NewsletterCommentControllerDocs {

    @Operation(summary = "뉴스레터 댓글 생성", description = "뉴스레터 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 댓글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.CREATE_NEWSLETTER_COMMENT",
                                    description = "댓글이 생성되었습니다."
                            )))} )
    @PostMapping("/{postId}")
    TtoklipResponse<Message> register(@PathVariable Long postId, @RequestBody CommentCreate request);

    @Operation(summary = "뉴스레터 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 댓글 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.REPORT_NEWSLETTER_COMMENT",
                                    description = "신고가 완료되었습니다."
                            )))} )
    @PostMapping("/report/{commentId}")
    TtoklipResponse<Message> report(@PathVariable Long commentId, @RequestBody ReportWebCreate request);

    @Operation(summary = "뉴스레터 댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "뉴스레터 댓글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "NewsletterResponseConstant.DELETE_NEWSLETTER_COMMENT",
                                    description = "댓글이 삭제되었습니다."
                            )))} )
    @DeleteMapping("/{commentId}")
    TtoklipResponse<Message> delete(@PathVariable Long commentId);
}
