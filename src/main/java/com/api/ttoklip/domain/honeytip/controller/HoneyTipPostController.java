package com.api.ttoklip.domain.honeytip.controller;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.honeytip.constant.HoneyTipResponseConstant;
import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipCreateRequest;
import com.api.ttoklip.domain.honeytip.controller.dto.request.HoneyTipEditReq;
import com.api.ttoklip.domain.honeytip.controller.dto.response.HoneyTipSingleResponse;
import com.api.ttoklip.domain.honeytip.facade.HoneyTipLikeFacade;
import com.api.ttoklip.domain.honeytip.facade.HoneyTipPostFacade;
import com.api.ttoklip.domain.honeytip.facade.HoneyTipScrapFacade;
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

@Tag(name = "HoneyTip Post", description = "꿀팁공유해요 게시판 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/honeytips/posts")
public class HoneyTipPostController {

    private final HoneyTipPostFacade honeytipPostFacade;
    private final HoneyTipLikeFacade honeyTipLikeFacade;
    private final HoneyTipScrapFacade honeyTipScrapFacade;

    /* CREATE */
    @Operation(summary = "새로운 꿀팁 생성", description = "form/data로 새로운 꿀팁 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.CREATE_HONEY_TIP,
                                    description = "꿀팁이 생성되었습니다."
                            )))})
    @PostMapping
    public SuccessResponse<Message> register(final @Validated @ModelAttribute HoneyTipCreateRequest request) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeytipPostFacade.register(request, currentMemberId));
    }


    /* UPDATE */
    @Operation(summary = "꿀팁 게시글 수정", description = "꿀팁 공유해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 게시글 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.EDIT_HONEY_TIP,
                                    description = "꿀팁이 수정되었습니다."
                            )))})
    @PatchMapping("/{postId}")
    public SuccessResponse<Message> edit(final @PathVariable Long postId,
                                         final @ModelAttribute HoneyTipEditReq request) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeytipPostFacade.edit(postId, request, currentMemberId));
    }


    /* DELETE */
    @Operation(summary = "꿀팁 게시글 삭제", description = "꿀팁 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 게시글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.DELETE_HONEY_TIP,
                                    description = "꿀팁이 삭제되었습니다."
                            )))})
    @DeleteMapping("/{postId}")
    public SuccessResponse<Message> delete(final @PathVariable Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeytipPostFacade.delete(postId, currentMemberId));
    }

    /* REPORT */
    @Operation(summary = "꿀팁공유해요 게시글 신고", description = "꿀팁 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.REPORT_HONEY_TIP,
                                    description = "꿀팁을 신고했습니다."
                            )))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Message> report(final @PathVariable Long postId,
                                           final @RequestBody ReportCreateRequest request) {
        return new SuccessResponse<>(honeytipPostFacade.report(postId, request));
    }

    /* LIKE */
    @Operation(summary = "꿀팁공유해요 도움이되었어요 추가", description = "꿀팁 ID에 해당하는 게시글을 도움이되었어요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도움이되었어요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.REGISTER_LIKE,
                                    description = "꿀팁을 도움이 되었어요를 추가했습니다."
                            )))})
    @PostMapping("/like/{postId}")
    public SuccessResponse<Message> registerLike(final @PathVariable Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeyTipLikeFacade.register(postId, currentMemberId));
    }

    @Operation(summary = "꿀팁공유해요 도움이되었어요 취소", description = "꿀팁 ID에 해당하는 게시글을 도움이되었어요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도움이되었어요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.CANCEL,
                                    description = "꿀팁을 도움이 되었어요를 취소했습니다."
                            )))})
    @DeleteMapping("/like/{postId}")
    public SuccessResponse<Message> cancelLike(final @PathVariable Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeyTipLikeFacade.cancel(postId, currentMemberId));
    }

    /* SCRAP */
    @Operation(summary = "꿀팁공유해요 스크랩 추가", description = "꿀팁 ID에 해당하는 게시글에 스크랩을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.REGISTER_SCRAP,
                                    description = "꿀팁의 스크랩을 추가했습니다."
                            )))})
    @PostMapping("/scrap/{postId}")
    public SuccessResponse<Message> registerScrap(final @PathVariable Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeyTipScrapFacade.register(postId, currentMemberId));
    }

    @Operation(summary = "꿀팁공유해요 스크랩 취소", description = "꿀팁 ID에 해당하는 게시글에 스크랩을 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.CANCEL_SCRAP,
                                    description = "꿀팁의 스크랩을 취소했습니다."
                            )))})
    @DeleteMapping("/scrap/{postId}")
    public SuccessResponse<Message> cancelScrap(final @PathVariable Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        return new SuccessResponse<>(honeyTipScrapFacade.cancel(postId, currentMemberId));
    }

    /* READ */
    @Operation(summary = "꿀팁 게시글 조회", description = "꿀팁 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "꿀팁 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = HoneyTipResponseConstant.CREATE_HONEY_TIP,
                                    description = "꿀팁이 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public SuccessResponse<HoneyTipSingleResponse> getSinglePost(final @PathVariable Long postId) {
        Long currentMemberId = getCurrentMember().getId();
        HoneyTipSingleResponse response = honeytipPostFacade.getSinglePost(postId, currentMemberId);
        return new SuccessResponse<>(response);
    }

}
