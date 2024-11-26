package com.api.honeytip.presentation;

import com.api.common.ReportWebCreate;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.honeytip.presentation.request.HoneyTipWebCreate;
import com.api.honeytip.presentation.request.HoneyTipWebEdit;
import com.api.honeytip.presentation.response.HoneyTipSingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "HoneyTip Post", description = "꿀팁공유해요 게시판 API")
public interface HoneyTipPostControllerDocs {

    @Operation(summary = "새로운 꿀팁 생성", description = "form/data로 새로운 꿀팁 게시글을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁 게시글 생성 성공",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.CREATE_HONEY_TIP",
                                    description = "꿀팁이 생성되었습니다."
                            )))} )
    TtoklipResponse<Message> register(@Validated @ModelAttribute HoneyTipWebCreate request);

    @Operation(summary = "꿀팁 게시글 수정", description = "꿀팁 공유해요 게시글을 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁 게시글 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.EDIT_HONEY_TIP",
                                    description = "꿀팁이 수정되었습니다."
                            )))} )
    TtoklipResponse<Message> edit(@PathVariable Long postId, @ModelAttribute HoneyTipWebEdit request);

    @Operation(summary = "꿀팁 게시글 삭제", description = "꿀팁 ID에 해당하는 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁 게시글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.DELETE_HONEY_TIP",
                                    description = "꿀팁이 삭제되었습니다."
                            )))} )
    TtoklipResponse<Message> delete(@PathVariable Long postId);

    @Operation(summary = "꿀팁 게시글 신고", description = "꿀팁 ID에 해당하는 게시글을 신고합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁 신고 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.REPORT_HONEY_TIP",
                                    description = "꿀팁을 신고했습니다."
                            )))} )
    TtoklipResponse<Message> report(@PathVariable Long postId, @RequestBody ReportWebCreate request);

    @Operation(summary = "도움이되었어요 추가", description = "꿀팁 ID에 해당하는 게시글에 도움이되었어요를 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "도움이되었어요 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.REGISTER_LIKE",
                                    description = "꿀팁을 도움이 되었어요를 추가했습니다."
                            )))} )
    TtoklipResponse<Message> registerLike(@PathVariable Long postId);

    @Operation(summary = "도움이되었어요 취소", description = "꿀팁 ID에 해당하는 게시글에 도움이되었어요를 취소합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "도움이되었어요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.CANCEL",
                                    description = "꿀팁을 도움이 되었어요를 취소했습니다."
                            )))} )
    TtoklipResponse<Message> cancelLike(@PathVariable Long postId);

    @Operation(summary = "스크랩 추가", description = "꿀팁 ID에 해당하는 게시글에 스크랩을 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.REGISTER_SCRAP",
                                    description = "꿀팁의 스크랩을 추가했습니다."
                            )))} )
    TtoklipResponse<Message> registerScrap(@PathVariable Long postId);

    @Operation(summary = "스크랩 취소", description = "꿀팁 ID에 해당하는 게시글에 스크랩을 취소합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "스크랩 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.CANCEL_SCRAP",
                                    description = "꿀팁의 스크랩을 취소했습니다."
                            )))} )
    TtoklipResponse<Message> cancelScrap(@PathVariable Long postId);

    @Operation(summary = "꿀팁 게시글 조회", description = "꿀팁 ID에 해당하는 게시글을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "꿀팁 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "HoneyTipResponseConstant.CREATE_HONEY_TIP",
                                    description = "꿀팁이 조회되었습니다."
                            )))} )
    TtoklipResponse<HoneyTipSingleResponse> getSinglePost(@PathVariable Long postId);
}
