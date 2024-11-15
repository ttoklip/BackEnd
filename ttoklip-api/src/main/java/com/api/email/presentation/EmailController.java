package com.api.email.presentation;

import com.api.email.application.EmailFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final EmailFacade emailFacade;

    /* --------------------------------- send authentication code --------------------------------- */
    @Operation(summary = "인증코드 메일 발송", description = "인증코드를 메일로 발송합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/send")
    public TtoklipResponse<Message> mailSend(final @RequestBody EmailSendRequest request) {
        log.info("EmailController.mailSend()");
        emailFacade.sendEmail(request.email());
        return new TtoklipResponse<>(Message.sendEmail());
    }

    /* --------------------------------- verify --------------------------------- */
    @Operation(summary = "인증코드 검증", description = "인증코드를 검증합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping("/verify")
    public TtoklipResponse<Message> verify(final @RequestBody EmailVerifyRequest request) {
        log.info("EmailController.verify()");
        emailFacade.verifyEmailCode(request);
        return new TtoklipResponse<>(Message.verifyCodeSuccess());
    }
}
