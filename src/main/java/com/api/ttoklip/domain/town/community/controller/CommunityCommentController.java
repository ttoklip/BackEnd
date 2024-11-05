package com.api.ttoklip.domain.town.community.controller;

import com.api.ttoklip.domain.common.comment.dto.request.CommentCreateRequest;
import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.town.community.facade.CommunityCommentFacade;
import com.api.ttoklip.domain.town.community.service.CommunityCommentService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

@Tag(name = "Town", description = "우리동네 - 소통해요 댓글 API 입니다.")
@RestController
@RequestMapping("api/v1/town/comms/comment")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentFacade communityCommentFacade;

    // 소통해요(community) 댓글

    /* CREATE */
    @Operation(summary = "소통해요 댓글 생성", description = "소통해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/{postId}")
    public SuccessResponse<Message> register(final @PathVariable Long postId,
                                             final @RequestBody CommentCreateRequest request) {
        Long currentMemberId = getCurrentMember().getId();
        Message message = communityCommentFacade.register(postId, request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* REPORT */
    @Operation(summary = "소통해요 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class)
                    ))})
    @PostMapping("/report/{commentId}")
    public SuccessResponse<Message> report(final @PathVariable Long commentId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = communityCommentFacade.report(commentId, request);
        return new SuccessResponse<>(message);
    }

//    /* UPDATE 미사용 */
//    @Operation(summary = "소통해요 댓글 수정", description = "소통해요 게시글 댓글을 수정합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "소통해요 댓글 수정 성공",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = SuccessResponse.class))),
//    })
//    @PatchMapping("/{commentId}")
//    public SuccessResponse<Long> edit(final @PathVariable Long commentId,
//                                      final @RequestBody CommentEditRequest request) {
//        communityCommentService.edit(commentId, request);
//        return new SuccessResponse<>(commentId);
//    }

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
        Message message = communityCommentFacade.delete(commentId);
        return new SuccessResponse<>(message);
    }
}
