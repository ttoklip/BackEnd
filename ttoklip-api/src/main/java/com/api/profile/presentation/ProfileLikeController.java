package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileLikeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileLikeController implements ProfileLikeControllerDocs {

    private final ProfileLikeFacade profileLikeFacade;

    @Override
    @PostMapping("/like")
    public TtoklipResponse<Message> registerLike(
            final @RequestParam Long targetMemberId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.created(
                profileLikeFacade.registerProfileLike(targetMemberId, currentMemberId)
        );
    }

    @Override
    @PostMapping("/cancel")
    public TtoklipResponse<Message> cancelLike(
            final @RequestParam Long targetMemberId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        return TtoklipResponse.ok(
                profileLikeFacade.cancelProfileLike(targetMemberId, currentMemberId)
        );
    }

    @Override
    @GetMapping
    public TtoklipResponse<TargetMemberProfileResponse> getTargetProfile(
            final @RequestParam Long targetMemberId
    ) {
        return TtoklipResponse.ok(
                profileLikeFacade.getTargetMemberProfile(targetMemberId)
        );
    }
}
