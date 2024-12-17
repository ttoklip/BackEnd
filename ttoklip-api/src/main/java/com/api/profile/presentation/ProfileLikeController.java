package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileLikeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileLikeController implements ProfileLikeControllerDocs {

    private final ProfileLikeFacade profileLikeFacade;

    @Override
    @PostMapping("/like")
    public TtoklipResponse<Message> registerLike(@RequestParam Long targetMemberId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileLikeFacade.registerProfileLike(targetMemberId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @PostMapping("/cancel")
    public TtoklipResponse<Message> cancelLike(@RequestParam Long targetMemberId) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileLikeFacade.cancelProfileLike(targetMemberId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    @Override
    @GetMapping
    public TtoklipResponse<TargetMemberProfileResponse> getTargetProfile(@RequestParam Long targetMemberId) {
        return new TtoklipResponse<>(profileLikeFacade.getTargetMemberProfile(targetMemberId));
    }
}
