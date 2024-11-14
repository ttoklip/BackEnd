package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileLikeFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Profile API", description = "프로필 관련 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileLikeController {

    private final ProfileLikeFacade profileLikeFacade;

    /* LIKE */
    @Operation(summary = "타인 프로필 좋아요 추가", description = "해당하는 사람의 프로필에 좋아요를 추가합니다.")
    @PostMapping("/like")
    public TtoklipResponse<Message> registerLike(
            @Parameter(description = "좋아요를 추가할 대상 회원의 ID", required = true, example = "1") final @RequestParam Long targetMemberId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileLikeFacade.registerProfileLike(targetMemberId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    /* LIKE 취소 */
    @Operation(summary = "타인 프로필 좋아요 취소", description = "해당하는 사람의 프로필에 좋아요를 취소합니다.")
    @PostMapping("/cancel")
    public TtoklipResponse<Message> cancelLike(
            @Parameter(description = "좋아요를 취소할 대상 회원의 ID", required = true, example = "1") final @RequestParam Long targetMemberId
    ) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileLikeFacade.cancelProfileLike(targetMemberId, currentMemberId);
        return new TtoklipResponse<>(message);
    }

    /* 타인 프로필 조회 */
    @Operation(summary = "타인 프로필 조회", description = "해당하는 사람의 프로필을 조회합니다.")
    @GetMapping
    public TtoklipResponse<TargetMemberProfileResponse> getTargetProfile(
            @Parameter(description = "조회할 회원의 ID", required = true, example = "1") final @RequestParam Long targetMemberId
    ) {
        return new TtoklipResponse<>(profileLikeFacade.getTargetMemberProfile(targetMemberId));
    }

}
