package com.api.newsletter.presentation;

import com.api.global.success.Message;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import com.api.newsletter.application.NewsletterCommentFacade;
import com.api.newsletter.presentation.response.NewsletterResponseConstant;
import com.domain.comment.domain.CommentCreate;
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

@Tag(name = "Newsletter Comment", description = "뉴스레터 댓글 API입니다.")
@RestController
@RequestMapping("/api/v1/newsletter/comment")
@RequiredArgsConstructor
public class NewsletterCommentController {

    private final NewsletterCommentFacade newsletterCommentFacade;

    /* CREATE */
    @Operation(summary = "새로운 댓글 생성", description = "지정된 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뉴스레터 댓글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.CREATE_NEWSLETTER_COMMENT,
                                    description = "댓글이 생성되었습니다."
                            )))})
    @PostMapping("/{postId}")
    public SuccessResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.register(postId, request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* REPORT */
    @Operation(summary = "뉴스레터 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "질문 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NewsletterResponseConstant.REPORT_NEWSLETTER_COMMENT,
                                    description = "신고가 완료되었습니다."
                            )))})
    @PostMapping("/report/{commentId}")
    public SuccessResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = newsletterCommentFacade.report(commentId, request);
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
                                    value = NewsletterResponseConstant.DELETE_NEWSLETTER_COMMENT,
                                    description = "댓글이 삭제되었습니다."
                            )))})
    @DeleteMapping("/{commentId}")
    public SuccessResponse<Message> delete(final @PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = newsletterCommentFacade.delete(commentId, currentMemberId);
        return new SuccessResponse<>(message);
    }

}
