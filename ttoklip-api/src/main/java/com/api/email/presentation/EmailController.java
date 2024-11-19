package com.api.email.presentation;

import com.api.email.application.EmailFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController implements EmailControllerDocs {

    private final EmailFacade emailFacade;

    @Override
    public TtoklipResponse<Message> mailSend(EmailSendRequest request) {
        log.info("EmailController.mailSend()");
        emailFacade.sendEmail(request.email());
        return new TtoklipResponse<>(Message.sendEmail());
    }

    @Override
    public TtoklipResponse<Message> verify(EmailVerifyRequest request) {
        log.info("EmailController.verify()");
        emailFacade.verifyEmailCode(request);
        return new TtoklipResponse<>(Message.verifyCodeSuccess());
    }
}
