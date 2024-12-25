package com.api.auth.local.presentation;

import com.api.auth.local.application.AuthFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.term.response.TermSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class LocalAuthController implements LocalAuthControllerDocs {

    private final AuthFacade authFacade;

    @Override
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> signup(
            final @Validated @ModelAttribute LocalMemberWebCreate request
    ) {
        return TtoklipResponse.created(
                authFacade.signup(request)
        );
    }

    @Override
    @PostMapping("/duplicate")
    public TtoklipResponse<Message> duplicate(
            final @RequestParam String newId
    ) {
        return TtoklipResponse.ok(
                authFacade.duplicate(newId)
        );
    }

    @Override
    @PostMapping("/login")
    public TtoklipResponse<AuthLoginResponse> login(
            final @RequestBody AuthLogin authLogin
    ) {
        return TtoklipResponse.ok(
                authFacade.login(authLogin)
        );
    }

    @Override
    @GetMapping("/agree")
    public TtoklipResponse<TermSignUpResponse> getTermSignUp() {
        return TtoklipResponse.ok(
                authFacade.getTermWhenSignUp()
        );
    }
}
