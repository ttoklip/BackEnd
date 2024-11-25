package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.comment.domain.CommentCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Community Comment", description = "함께해요 댓글 관리 API")
public interface CommunityCommentControllerDocs {

    @Operation(summary = "소통해요 댓글 생성", description = "소통해요 게시글에 댓글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 댓글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class)
                    ))})
    TtoklipResponse<Message> register(@PathVariable Long postId, @RequestBody CommentCreate request);

    @Operation(summary = "소통해요 댓글 신고", description = "댓글 ID로 댓글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 댓글 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class)
                    ))})
    TtoklipResponse<Message> report(@PathVariable Long commentId, @RequestBody ReportWebCreate request);

    @Operation(summary = "소통해요 댓글 삭제", description = "댓글 ID로 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "소통해요 댓글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class)
                    ))})
    TtoklipResponse<Message> delete(@PathVariable Long commentId);
}
