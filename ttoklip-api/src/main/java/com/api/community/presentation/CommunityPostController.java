package com.api.community.presentation;

import com.api.common.ReportWebCreate;
import com.api.community.application.CommunityPostFacade;
import com.api.community.presentation.dto.request.CommunityWebCreate;
import com.api.community.presentation.dto.request.CommunityWebEdit;
import com.api.community.presentation.dto.response.CommunityResponse;
import com.api.global.success.Message;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Community", description = "우리동네 - 소통해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/town/comms")
public class CommunityPostController {

    private final CommunityPostFacade communityPostFacade;

    /* CREATE */
    @Operation(summary = "소통해요 게시글 생성",
            description = "소통해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.createAndDeleteCommunity,
                                    description = "소통해요 게시글이 생성되었습니다."
                            )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Message> register(final @Validated @ModelAttribute CommunityWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.register(request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* READ */
    @Operation(summary = "소통해요 게시글 조회",
            description = "소통해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommunityResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.readSingleCommunity,
                                    description = "소통해요 게시글이 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public SuccessResponse<CommunityResponse> getSinglePost(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CommunityResponse response = communityPostFacade.getSinglePost(postId, currentMemberId);
        return new SuccessResponse<>(response);
    }

    /* UPDATE */
    @Operation(summary = "소통해요 게시글 수정",
            description = "소통해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.updateCommunity,
                                    description = "소통해요 게시글이 수정되었습니다."
                            )))})
    @PatchMapping("/{postId}")
    public SuccessResponse<Message> edit(final @PathVariable Long postId,
                                         final @Validated @ModelAttribute CommunityWebEdit request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.edit(postId, request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* DELETE */
    @Operation(summary = "소통해요 게시글 삭제", description = "소통해요 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 게시글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.deleteCommunity,
                                    description = "소통해요 게시글을 삭제했습니다."
                            )))})
    @DeleteMapping("/{postId}")
    public SuccessResponse<Message> delete(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return new SuccessResponse<>(communityPostFacade.delete(postId, currentMemberId));
    }

    /* REPORT */
    @Operation(summary = "소통해요 게시글 신고", description = "소통해요 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소통해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.REPORT_COMMUNITY,
                                    description = "소통해요 게시글을 신고했습니다."
                            )))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Message> report(final @PathVariable Long postId,
                                           final @RequestBody ReportWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.report(postId, request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* LIKE */
    @Operation(summary = "소통해요 좋아요 추가", description = "소통해요 ID에 해당하는 게시글에 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.REGISTER_LIKE,
                                    description = "소통해요에 좋아요를 추가했습니다."
                            )))})
    @PostMapping("/like/{postId}")
    public SuccessResponse<Message> registerLike(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.registerLike(postId, currentMemberId);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "소통해요 좋아요 취소", description = "소통해요 ID에 해당하는 게시글에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.CANCEL_LIKE,
                                    description = "소통해요에 좋아요를 취소했습니다."
                            )))})
    @DeleteMapping("/like/{postId}")
    public SuccessResponse<Message> cancelLike(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.cancelLike(postId, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* SCRAP */
    @Operation(summary = "소통해요 스크랩 추가", description = "소통해요 ID에 해당하는 게시글에 스크랩을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.REGISTER_SCRAP,
                                    description = "소통해요에 스크랩을 추가했습니다."
                            )))})
    @PostMapping("/scrap/{postId}")
    public SuccessResponse<Message> registerScrap(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.registerScrap(postId, currentMemberId);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "소통해요 스크랩 취소", description = "소통해요 ID에 해당하는 게시글에 스크랩을 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CommunityResponseConstant.CANCEL_SCRAP,
                                    description = "소통해요에 스크랩을 취소했습니다."
                            )))})
    @DeleteMapping("/scrap/{postId}")
    public SuccessResponse<Message> cancelScrap(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = communityPostFacade.cancelScrap(postId, currentMemberId);
        return new SuccessResponse<>(message);
    }
}
