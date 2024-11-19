package com.api.email.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Email", description = "메일 인증 관련 API")
@RequestMapping("/api/v1/email")
public interface EmailControllerDocs {

    @Operation(summary = "인증코드 메일 발송", description = "인증코드를 메일로 발송합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/send")
    TtoklipResponse<Message> mailSend(@RequestBody EmailSendRequest request);

    @Operation(summary = "인증코드 검증", description = "인증코드를 검증합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/verify")
    TtoklipResponse<Message> verify(@RequestBody EmailVerifyRequest request);
}
