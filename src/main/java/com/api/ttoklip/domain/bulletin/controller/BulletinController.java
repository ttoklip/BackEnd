package com.api.ttoklip.domain.bulletin.controller;

import com.api.ttoklip.domain.bulletin.constant.NotiConstant;
import com.api.ttoklip.domain.bulletin.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.bulletin.dto.request.NoticeEditRequest;
import com.api.ttoklip.domain.bulletin.dto.response.NoticePaging;
import com.api.ttoklip.domain.bulletin.dto.response.NoticeResponse;
import com.api.ttoklip.domain.bulletin.service.NoticeService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notice", description = "공지사항 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class BulletinController {

    private final static int PAGE_SIZE = 10; // 페이지 당 데이터 수
    private final NoticeService noticeService;

    @Operation(summary = "모든 공지사항 불러오기", description = "공지사항 목록을 가져옵니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.noticeResponse,
                                    description = "공지사항이 조회되었습니다"
                            )))})
    @GetMapping()
    public SuccessResponse<NoticePaging> getNoticeList(
            @Parameter(description = "페이지 번호 (0부터 시작, 기본값 0)", example = "0")
            @RequestParam(required = false, defaultValue = "0") final int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        NoticePaging noticePaging = noticeService.getNoticeList(pageable);
        return new SuccessResponse<>(noticePaging);
    }

    @Operation(summary = "공지사항 생성", description = "공지사항을 만듭니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 생성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.createNoticeResponse,
                                    description = "공지사항이 생성되었습니다"
                            )))})
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<Message> register(final @Validated @RequestBody NoticeCreateRequest request) {
        Message message = noticeService.register(request);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "공지사항 조회", description = "공지사항 ID에 해당하는 공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.singleNoticeResponse,
                                    description = "공지사항이 조회되었습니다."
                            )))})
    @GetMapping("/{noticeId}")
    public SuccessResponse<NoticeResponse> getSingleNotice(final @PathVariable Long noticeId) {
        NoticeResponse response = noticeService.getSingleNotice(noticeId);
        return new SuccessResponse<>(response);
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항 ID에 해당하는 공지사항을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.deleteNoticeResponse,
                                    description = "공지사항을 삭제하였습니다"
                            )))})
    @DeleteMapping("/{noticeId}")
    public SuccessResponse<Message> deleteNotice(final @PathVariable Long noticeId) {
        Message message = noticeService.deleteNotice(noticeId);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "공지사항 수정", description = "공지사항 ID에 해당하는 공지사항을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = NotiConstant.updateNoticeResponse,
                                    description = "공지사항이 수정되었습니다."
                            )))})
    @PatchMapping("/{noticeId}")
    public SuccessResponse<Message> edit(final @PathVariable Long noticeId,
                                         final @RequestBody NoticeEditRequest request) {
        Message message = noticeService.edit(noticeId, request);
        return new SuccessResponse<>(message);
    }
}
