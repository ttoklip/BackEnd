package com.api.cart.presentation;

import com.api.cart.presentation.dto.request.CartWebCreate;
import com.api.cart.presentation.dto.request.UpdateStatusRequest;
import com.api.cart.presentation.dto.response.CartGroupMemberResponse;
import com.api.cart.presentation.dto.response.CartResponse;
import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "우리동네 - 함께해요 API")
public interface CartPostControllerDocs {

    @Operation(summary = "함께해요 게시글 생성", description = "함께해요 게시글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "함께해요 게시글 생성 성공",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CartResponseConstant.createAndDeleteCart",
                                    description = "함께해요 게시글이 생성되었습니다."
                            )))} )
    TtoklipResponse<Message> register(@Validated @ModelAttribute CartWebCreate request);

    @Operation(summary = "함께해요 게시글 조회", description = "함께해요 단일 게시글을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "함께해요 게시글 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CartResponseConstant.readSingleCart",
                                    description = "함께해요 게시글이 조회되었습니다."
                            )))} )
    TtoklipResponse<CartResponse> getSinglePost(@PathVariable Long postId);

    @Operation(summary = "함께해요 게시글 수정", description = "함께해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "함께해요 게시글 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CartResponseConstant.updateCart",
                                    description = "함께해요 게시글이 수정되었습니다."
                            )))} )
    TtoklipResponse<Message> edit(@PathVariable Long postId, @Validated @ModelAttribute CartWebCreate request);

    @Operation(summary = "함께해요 게시글 신고", description = "함께해요 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "함께해요 신고 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CartResponseConstant.REPORT_CART",
                                    description = "함께해요 게시글을 신고했습니다."
                            )))} )
    TtoklipResponse<Message> report(@PathVariable Long postId, @RequestBody ReportWebCreate request);

    @Operation(summary = "함께해요 게시글 상태 수정", description = "함께해요 게시글의 상태를 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "함께해요 게시글 상태 수정 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "CartResponseConstant.STATUS_CART",
                                    description = "함께해요 게시글의 상태를 마감으로 수정했습니다."
                            )))} )
    TtoklipResponse<Message> updateStatus(@PathVariable Long postId, @RequestBody UpdateStatusRequest request);

    @Operation(summary = "참여자 추가", description = "특정 카트에 참여자를 추가합니다.")
    TtoklipResponse<Message> addParticipant(@PathVariable Long cartId);

    @Operation(summary = "참여 취소", description = "특정 카트에서 참여를 취소합니다.")
    TtoklipResponse<Message> removeParticipant(@PathVariable Long cartId);

    @Operation(summary = "참여자 수 확인", description = "특정 카트의 참여자 수를 확인합니다.")
    TtoklipResponse<Long> countParticipants(@PathVariable Long cartId);

    @Operation(summary = "참여자 확인", description = "특정 카트의 참여자를 확인합니다.")
    TtoklipResponse<CartGroupMemberResponse> checkParticipants(@PathVariable Long cartId);
}
