package com.api.cart.presentation;

import com.api.cart.application.CartPostFacade;
import com.api.cart.presentation.dto.request.CartWebCreate;
import com.api.cart.presentation.dto.request.UpdateStatusRequest;
import com.api.cart.presentation.dto.response.CartGroupMemberResponse;
import com.api.cart.presentation.dto.response.CartResponse;
import com.api.global.success.Message;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import com.domain.cart.domain.vo.TradeStatus;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "우리동네 - 함께해요 API 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/town/carts")
public class CartPostController {

    private final CartPostFacade cartPostFacade;

    // 함께해요 - cart

    /* CREATE */
    @Operation(summary = "함께해요 게시글 생성",
            description = "함께해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.createAndDeleteCart,
                                    description = "함께해요 게시글이 생성되었습니다."
                            )))})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Message> register(final @Validated @ModelAttribute CartWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.register(request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* READ */
    @Operation(summary = "함께해요 게시글 조회",
            description = "함께해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.readSingleCart,
                                    description = "함께해요 게시글이 조회되었습니다."
                            )))})
    @GetMapping("/{postId}")
    public SuccessResponse<CartResponse> getSinglePost(final @PathVariable Long postId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CartResponse response = cartPostFacade.getSinglePost(postId, currentMemberId);
        return new SuccessResponse<>(response);
    }

    /* UPDATE */
    @Operation(summary = "함께해요 게시글 수정",
            description = "함께해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.updateCart,
                                    description = "함께해요 게시글이 수정되었습니다."
                            )))})
    @PatchMapping("/{postId}")
    public SuccessResponse<Message> edit(final @PathVariable Long postId,
                                         final @Validated @ModelAttribute CartWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.edit(postId, request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* REPORT */
    @Operation(summary = "함께해요 게시글 신고", description = "함께해요 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.REPORT_CART,
                                    description = "함께해요 게시글을 신고했습니다."
                            )))})
    @PostMapping("/report/{postId}")
    public SuccessResponse<Message> report(final @PathVariable Long postId,
                                           final @RequestBody ReportCreateRequest request) {
        Message message = cartPostFacade.report(postId, request);
        return new SuccessResponse<>(message);
    }

    /* UPDATE STATUS */
    @Operation(summary = "함께해요 게시글 상태 수정", description = "함께해요 게시글의 상태를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "함께해요 게시글 상태 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.STATUS_CART,
                                    description = "함께해요 게시글의 상태를 마감으로 수정했습니다."
                            )))})
    @PatchMapping("/{postId}/status")
    public SuccessResponse<Message> updateStatus(final @PathVariable Long postId,
                                                 final @RequestBody UpdateStatusRequest request) {
        Message message = cartPostFacade.updateStatus(postId, TradeStatus.valueOf(request.getStatus()));
        return new SuccessResponse<>(message);
    }

    /* 참여자 추가 */
    @Operation(summary = "참여자 추가", description = "특정 카트에 참여자를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여자 추가 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.ADD_PARTICIPANT,
                                    description = "참여자가 추가되었습니다."
                            )))})
    @PostMapping("/participants/{cartId}")
    public SuccessResponse<Message> addParticipant(final @PathVariable Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.addParticipant(cartId, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* 참여 취소 */
    @Operation(summary = "참여 취소", description = "특정 카트에서 참여자를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여 취소 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.REMOVE_PARTICIPANT,
                                    description = "참여를 취소되었습니다."
                            )))})
    @DeleteMapping("/participants/{cartId}")
    public SuccessResponse<Message> removeParticipant(final @PathVariable Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = cartPostFacade.removeParticipant(cartId, currentMemberId);
        return new SuccessResponse<>(message);
    }

    /* 참여자 수 확인 */
    @Operation(summary = "참여자 수 확인", description = "특정 카트의 참여자 수를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여자 수 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.COUNT_PARTICIPANTS,
                                    description = "참여자 수를 확인하였습니다."
                            )))})
    @GetMapping("/participants/count/{cartId}")
    public SuccessResponse<Long> countParticipants(final @PathVariable Long cartId) {
        Long count = cartPostFacade.countParticipants(cartId);
        return new SuccessResponse<>(count);
    }

    @Operation(summary = "참여자 확인", description = "특정 카트의 참여자를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "참여자 확인 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = CartResponseConstant.CHECK_PARTICIPANTS,
                                    description = "참여자를 확인하였습니다."
                            )))})
    @GetMapping("/participants/members/{cartId}")
    public SuccessResponse<CartGroupMemberResponse> checkParticipants(final @PathVariable Long cartId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        CartGroupMemberResponse cartGroupMemberResponse = cartPostFacade.checkParticipants(cartId, currentMemberId);
        return new SuccessResponse<>(cartGroupMemberResponse);
    }

}