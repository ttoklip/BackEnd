package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/privacy")
public class ProfileController implements ProfileControllerDocs {

    private final ProfileFacade profileFacade;

    @Override
    @PostMapping("/insert")
    public TtoklipResponse<Message> register(@ModelAttribute @Validated ProfileWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileFacade.insert(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/oauth/check-nickname")
    public TtoklipResponse<Message> checkOauthNickname(@RequestBody Nickname nickname) {
        Message message = profileFacade.validNickname(nickname);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping("/local/check-nickname")
    public TtoklipResponse<Message> checkLocalNickname(@RequestBody Nickname nickname) {
        Message message = profileFacade.validNickname(nickname);
        return new TtoklipResponse<>(message);
    }
}
