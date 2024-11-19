package com.api.auth.local.presentation;

import com.api.auth.local.application.AuthFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.term.response.TermSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LocalAuthController implements LocalAuthControllerDocs {

    private final AuthFacade authFacade;

    @Override
    public TtoklipResponse<Message> signup(LocalMemberWebCreate request) {
        return new TtoklipResponse<>(authFacade.signup(request));
    }

    @Override
    public TtoklipResponse<Message> duplicate(String newId) {
        return new TtoklipResponse<>(authFacade.duplicate(newId));
    }

    @Override
    public TtoklipResponse<AuthLoginResponse> login(AuthLogin authLogin) {
        AuthLoginResponse response = authFacade.login(authLogin);
        return new TtoklipResponse<>(response);
    }

    @Override
    public TtoklipResponse<TermSignUpResponse> getTermSignUp() {
        TermSignUpResponse response = authFacade.getTermWhenSignUp();
        return new TtoklipResponse<>(response);
    }
}
