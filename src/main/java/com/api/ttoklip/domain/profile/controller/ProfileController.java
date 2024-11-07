package com.api.ttoklip.domain.profile.controller;

import com.api.ttoklip.domain.profile.controller.response.TargetMemberProfile;
import com.api.ttoklip.domain.profile.facade.ProfileLikeFacade;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
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
public class ProfileController {

    private final ProfileLikeFacade profileLikeFacade;

    /* LIKE */
    @Operation(summary = "타인 프로필 좋아요 추가", description = "해당하는 사람의 프로필에 좋아요를 추가합니다.")
    @PostMapping("/like")
    public SuccessResponse<Message> registerLike(
            @Parameter(description = "좋아요를 추가할 대상 회원의 ID", required = true, example = "1") final @RequestParam Long targetMemberId
    ) {
        Message message = profileLikeFacade.registerProfileLike(targetMemberId);
        return new SuccessResponse<>(message);
    }

    /* LIKE 취소 */
    @Operation(summary = "타인 프로필 좋아요 취소", description = "해당하는 사람의 프로필에 좋아요를 취소합니다.")
    @PostMapping("/cancel")
    public SuccessResponse<Message> cancelLike(
            @Parameter(description = "좋아요를 취소할 대상 회원의 ID", required = true, example = "1") final @RequestParam Long targetMemberId
    ) {
        Message message = profileLikeFacade.cancelProfileLike(targetMemberId);
        return new SuccessResponse<>(message);
    }

    /* 타인 프로필 조회 */
    @Operation(summary = "타인 프로필 조회", description = "해당하는 사람의 프로필을 조회합니다.")
    @GetMapping
    public SuccessResponse<TargetMemberProfile> getTargetProfile(
            @Parameter(description = "조회할 회원의 ID", required = true, example = "1") final @RequestParam Long targetMemberId
    ) {
        return new SuccessResponse<>(profileLikeFacade.getTargetMemberProfile(targetMemberId));
    }

}
