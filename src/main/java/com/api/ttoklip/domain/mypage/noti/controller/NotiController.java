package com.api.ttoklip.domain.mypage.noti.controller;

import com.api.ttoklip.domain.mypage.main.constant.MyPageConstant;
import com.api.ttoklip.domain.mypage.noti.constant.NotiConstant;
import com.api.ttoklip.domain.mypage.noti.dto.response.NoticeListResponse;
import com.api.ttoklip.domain.mypage.noti.service.NotiService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지사항", description = "공지사항 api입니다")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notice")
public class NotiController {

    private final NotiService notiService;
    @Operation(summary = "공지사항 불러오기", description = "공지사항 목록을 가져옵니다")
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
}
