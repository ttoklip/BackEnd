package com.api.email.presentation;

import com.api.email.application.EmailFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController implements EmailControllerDocs {

    private final EmailFacade emailFacade;

    @Override
    @PostMapping("/send")
    public TtoklipResponse<Message> mailSend(
            final @RequestBody EmailSendRequest request
    ) {
        emailFacade.send(request.email());
        return TtoklipResponse.accepted(
                Message.sendEmail()
        );
    }

    @Override
    @PostMapping("/verify")
    public TtoklipResponse<Message> verify(
            final @RequestBody EmailVerifyRequest request
    ) {
        emailFacade.verifyEmailCode(request);
        return TtoklipResponse.ok(
                Message.verifyCodeSuccess()
        );
    }
}
