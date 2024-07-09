package com.api.ttoklip.domain.common.report.controller;

import com.api.ttoklip.domain.common.report.dto.ReportCreateRequest;
import com.api.ttoklip.domain.common.report.service.ReportService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Report list api", description = "회원을 신고하는 API입니다.")
@RestController
@RequestMapping("/api/v1/member/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /* CREATE */
    @Operation(summary = "회원 신고", description = "신고할 유저의 를 받아 신고합니다.")
    @PostMapping
    public SuccessResponse<Message> register(
            @RequestParam @Schema(description = "신고할 회원의 닉네임", example = "user123") String nickName,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "신고 요청 데이터", required = true,
                    content = @Content(schema = @Schema(implementation = ReportCreateRequest.class))) ReportCreateRequest request) {
        return new SuccessResponse<>(reportService.reportMember(request, nickName));
    }


}
