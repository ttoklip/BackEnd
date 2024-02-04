package com.api.ttoklip.domain.mypage.noti.post.controller;

import com.api.ttoklip.domain.main.constant.QuestionResponseConstant;
import com.api.ttoklip.domain.mypage.noti.post.constant.NotiConstant;
import com.api.ttoklip.domain.mypage.noti.post.dto.request.NoticeCreateRequest;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeListResponse;
import com.api.ttoklip.domain.mypage.noti.post.dto.response.NoticeResponse;
import com.api.ttoklip.domain.mypage.noti.post.service.NotiService;
import com.api.ttoklip.domain.question.post.dto.response.QuestionSingleResponse;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지사항", description = "공지사항 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class NotiController {

    private final NotiService notiService;
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
    @GetMapping("/notice")
    public SuccessResponse<NoticeListResponse> noticeList() {
        return new SuccessResponse<>(notiService.noticeList());
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
    @PostMapping(value = "/createNotice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Long> register(final @Validated @ModelAttribute NoticeCreateRequest request) {
        Long noticeId=notiService.register(request);
        return new SuccessResponse<>(noticeId);
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
                                    description = "질문이 조회되었습니다."
                            )))})
    @GetMapping("/{noticeId}")
    public SuccessResponse<NoticeResponse> getSingleNotice(final @PathVariable Long noticeId) {
        NoticeResponse response = notiService.getSingleNotice(noticeId);
        return new SuccessResponse<>(response);
    }
}
