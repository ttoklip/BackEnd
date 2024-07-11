package com.api.ttoklip.domain.email.controller;

import com.api.ttoklip.domain.email.dto.request.EmailSendRequest;
import com.api.ttoklip.domain.email.dto.request.EmailVerifyRequest;
import com.api.ttoklip.domain.email.service.EmailService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Email", description = "메일 인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    /* --------------------------------- send authentication code --------------------------------- */
    @Operation(summary = "인증코드 메일 발송", description = "인증코드를 메일로 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/send")
    public SuccessResponse<Message> mailSend(@RequestBody EmailSendRequest request) throws MessagingException, IOException {
        log.info("EmailController.mailSend()");
        emailService.sendEmail(request.getEmail());
        return new SuccessResponse<>(Message.sendEmail());
    }

    /* --------------------------------- verify --------------------------------- */
    @Operation(summary = "인증코드 검증", description = "인증코드를 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/verify")
    public SuccessResponse<Message> verify(@RequestBody EmailVerifyRequest request) {
        log.info("EmailController.verify()");
        emailService.verifyEmailCode(request);
        return new SuccessResponse<>(Message.verifyCodeSuccess());
    }
}
