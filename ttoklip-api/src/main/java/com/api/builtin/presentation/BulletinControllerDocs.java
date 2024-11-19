package com.api.builtin.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.bulletin.domain.NoticeCreate;
import com.domain.bulletin.domain.NoticeEdit;
import com.domain.bulletin.domain.NoticeResponse;
import com.domain.bulletin.domain.NoticeResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/notice")
public interface BulletinControllerDocs {

    @Operation(summary = "공지사항 목록 조회 API", description = "공지사항 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공지사항 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.noticeResponse,
                                    description = "공지사항이 조회되었습니다"
                            )))})

    @GetMapping
    TtoklipResponse<NoticeResponses> getNoticeList(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page);

    @Operation(summary = "공지사항 생성 API", description = "공지사항을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공지사항 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.createNoticeResponse,
                                    description = "공지사항이 생성되었습니다"
                            )))})

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    TtoklipResponse<Message> register(@Validated @RequestBody NoticeCreate request);

    @Operation(summary = "공지사항 단일 조회 API", description = "공지사항 ID에 해당하는 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공지사항 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.singleNoticeResponse,
                                    description = "공지사항이 조회되었습니다."
                            )))})

    @GetMapping("/{noticeId}")
    TtoklipResponse<NoticeResponse> getSingleNotice(@PathVariable Long noticeId);

    @Operation(summary = "공지사항 삭제 API", description = "공지사항 ID에 해당하는 공지사항을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공지사항 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.deleteNoticeResponse,
                                    description = "공지사항을 삭제하였습니다"
                            )))})

    @DeleteMapping("/{noticeId}")
    TtoklipResponse<Message> deleteNotice(@PathVariable Long noticeId);

    @Operation(summary = "공지사항 수정 API", description = "공지사항 ID에 해당하는 공지사항을 수정합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "공지사항 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.updateNoticeResponse,
                                    description = "공지사항이 수정되었습니다."
                            )))})

    @PatchMapping("/{noticeId}")
    TtoklipResponse<Message> edit(@PathVariable Long noticeId, @RequestBody NoticeEdit request);
}
