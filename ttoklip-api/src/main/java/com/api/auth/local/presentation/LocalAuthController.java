package com.api.auth.local.presentation;

import com.api.auth.local.application.AuthFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.term.response.TermSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class LocalAuthController implements LocalAuthControllerDocs {

    private final AuthFacade authFacade;

    @Override
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> signup(@Validated @ModelAttribute LocalMemberWebCreate request
    ) {
        return new TtoklipResponse<>(authFacade.signup(request));
    }

    @Override
    @PostMapping("/duplicate")
    public TtoklipResponse<Message> duplicate(@RequestParam String newId) {
        return new TtoklipResponse<>(authFacade.duplicate(newId));
    }

    @Override
    @PostMapping("/login")
    public TtoklipResponse<AuthLoginResponse> login(@RequestBody AuthLogin authLogin) {
        AuthLoginResponse response = authFacade.login(authLogin);
        return new TtoklipResponse<>(response);
    }

    @Override
    @GetMapping("/agree")
    public TtoklipResponse<TermSignUpResponse> getTermSignUp() {
        TermSignUpResponse response = authFacade.getTermWhenSignUp();
        return new TtoklipResponse<>(response);
    }
}
