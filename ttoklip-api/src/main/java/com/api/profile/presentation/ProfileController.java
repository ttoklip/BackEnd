package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerDocs {

    private final ProfileFacade profileFacade;

    @Override
    public TtoklipResponse<Message> register(ProfileWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileFacade.insert(request, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> checkOauthNickname(Nickname nickname) {
        Message message = profileFacade.validNickname(nickname);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> checkLocalNickname(Nickname nickname) {
        Message message = profileFacade.validNickname(nickname);
        return new TtoklipResponse<>(message);
    }
}
