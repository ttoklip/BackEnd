package com.api.question.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.question.application.QuestionCommentFacade;
import com.api.question.application.QuestionCommentLikeFacade;
import com.domain.comment.domain.CommentCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Tag(name = "Question Comment", description = "질문해요 댓글 API입니다.")
@RestController
@RequestMapping("/api/v1/question/comment")
@RequiredArgsConstructor
public class QuestionCommentController {

    private final QuestionCommentFacade questionCommentFacade;
    private final QuestionCommentLikeFacade questionCommentLikeFacade;

    /* CREATE */
    @Operation(summary = "새로운 댓글 생성", description = "지정된 게시글에 댓글을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.CREATE_QUESTION_COMMENT,
                                    description = "댓글이 생성되었습니다."
                            )))})
    @PostMapping("/{postId}")
    public TtoklipResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.register(postId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    /* REPORT */
    @Operation(summary = "질문 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.REPORT_QUESTION_COMMENT,
                                    description = "댓글이 생성되었습니다."
                            )))})
    @PostMapping("/report/{commentId}")
    public TtoklipResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.report(commentId, request, currentMemberId);
        return new TtoklipResponse<>(message);
    }


    /* DELETE */
    @Operation(summary = "댓글 삭제", description = "지정된 게시글에 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "질문 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.DELETE_QUESTION_COMMENT,
                                    description = "댓글이 삭제되었습니다."
                            )))})
    @DeleteMapping("/{commentId}")
    public TtoklipResponse<Message> delete(final @PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentFacade.delete(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    /* LIKE */
    @Operation(summary = "질문 댓글 좋아요 추가", description = "질문 ID에 해당하는 댓글에 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.REGISTER_LIKE,
                                    description = "질문 댓글의 좋아요를 추가했습니다."
                            )))})
    @PostMapping("/like/{commentId}")
    public TtoklipResponse<Message> registerLike(final @PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentLikeFacade.registerLike(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Operation(summary = "질문 댓글 좋아요 취소", description = "질문 ID에 해당하는 댓글에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "좋아요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = QuestionResponseConstant.CANCEL_LIKE,
                                    description = "꿀팁의 스크랩을 취소했습니다."
                            )))})
    @DeleteMapping("/like/{commentId}")
    public TtoklipResponse<Message> cancleLike(final @PathVariable Long commentId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = questionCommentLikeFacade.cancelLike(commentId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

}
