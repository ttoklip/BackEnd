package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/privacy")
public class ProfileController implements ProfileControllerDocs {

    private final ProfileFacade profileFacade;

    @Override
    @PostMapping("/insert")
    public TtoklipResponse<Message> register(
            final @ModelAttribute @Validated ProfileWebCreate request
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                profileFacade.insert(request, currentMemberId)
        );
    }

    @Override
    @PostMapping("/oauth/check-nickname")
    public TtoklipResponse<Message> checkOauthNickname(
            final @RequestBody Nickname nickname
    ) {
        return TtoklipResponse.ok(
                profileFacade.validNickname(nickname)
        );
    }

    @Override
    @PostMapping("/local/check-nickname")
    public TtoklipResponse<Message> checkLocalNickname(@RequestBody Nickname nickname) {
        return TtoklipResponse.ok(
                profileFacade.validNickname(nickname)
        );
    }
}
