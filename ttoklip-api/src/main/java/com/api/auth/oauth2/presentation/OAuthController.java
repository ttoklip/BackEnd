package com.api.auth.oauth2.presentation;

import com.api.auth.oauth2.application.OAuthFacade;
import com.api.global.support.response.TtoklipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OAuthController implements OAuthControllerDocs {

    private final OAuthFacade oAuthFacade;

    @Override
    @PostMapping
    public TtoklipResponse<OAuthLoginResponse> login(@RequestBody OAuthLogin request) {
        OAuthLoginResponse response = oAuthFacade.authenticate(request);
        return new TtoklipResponse<>(response);
    }
}
