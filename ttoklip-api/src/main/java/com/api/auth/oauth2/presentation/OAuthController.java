package com.api.auth.oauth2.presentation;

import com.api.auth.oauth2.application.OAuthFacade;
import com.api.global.support.response.TtoklipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OAuthController implements OAuthControllerDocs {

    private final OAuthFacade oAuthFacade;

    @Override
    public TtoklipResponse<OAuthLoginResponse> login(OAuthLogin request) {
        OAuthLoginResponse response = oAuthFacade.authenticate(request);
        return new TtoklipResponse<>(response);
    }
}
