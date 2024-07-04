package com.api.ttoklip.domain.email.controller;

import com.api.ttoklip.domain.email.dto.request.EmailRequest;
import com.api.ttoklip.domain.email.service.EmailService;
import com.api.ttoklip.global.exception.ApiExceptionResponse;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
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
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일입니다.",
                    content = @Content
                            (schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content
                            (schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/send")
    public SuccessResponse<String> mailSend(@RequestBody EmailRequest request) throws MessagingException {
        log.info("EmailController.mailSend()");
        emailService.sendEmail(request.getEmail());
        return new SuccessResponse<>("인증코드가 발송되었습니다.");
    }

    /* --------------------------------- verify --------------------------------- */
    @Operation(summary = "인증코드 인증", description = "인증코드를 인증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일입니다.",
                    content = @Content
                            (schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content
                            (schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/verify")
    public SuccessResponse<Message> verify(@RequestBody EmailRequest request) {
        log.info("EmailController.verify()");
        boolean isVerify = emailService.verifyEmailCode(request.getEmail(), request.getVerifyCode());
        return new SuccessResponse<>(isVerify ? Message.verifyCodeSuccess() : Message.verifyCodeFail());
    }
}
