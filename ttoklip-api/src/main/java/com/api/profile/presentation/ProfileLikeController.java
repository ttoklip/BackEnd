package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileLikeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileLikeController implements ProfileLikeControllerDocs {

    private final ProfileLikeFacade profileLikeFacade;

    @Override
    public TtoklipResponse<Message> registerLike(Long targetMemberId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileLikeFacade.registerProfileLike(targetMemberId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<Message> cancelLike(Long targetMemberId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileLikeFacade.cancelProfileLike(targetMemberId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    public TtoklipResponse<TargetMemberProfileResponse> getTargetProfile(Long targetMemberId) {
        return new TtoklipResponse<>(profileLikeFacade.getTargetMemberProfile(targetMemberId));
    }
}
