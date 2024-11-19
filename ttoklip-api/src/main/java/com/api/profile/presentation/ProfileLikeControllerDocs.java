package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Profile Like", description = "프로필 좋아요 관리 API")
@RequestMapping("/api/v1/profile")
public interface ProfileLikeControllerDocs {

    @Operation(summary = "타인 프로필 좋아요 추가", description = "해당하는 사람의 프로필에 좋아요를 추가합니다.")
    @PostMapping("/like")
    TtoklipResponse<Message> registerLike(
            @Parameter(description = "좋아요를 추가할 대상 회원의 ID", required = true, example = "1")
            @RequestParam Long targetMemberId
    );

    @Operation(summary = "타인 프로필 좋아요 취소", description = "해당하는 사람의 프로필에 좋아요를 취소합니다.")
    @PostMapping("/cancel")
    TtoklipResponse<Message> cancelLike(
            @Parameter(description = "좋아요를 취소할 대상 회원의 ID", required = true, example = "1")
            @RequestParam Long targetMemberId
    );

    @Operation(summary = "타인 프로필 조회", description = "해당하는 사람의 프로필을 조회합니다.")
    @GetMapping
    TtoklipResponse<TargetMemberProfileResponse> getTargetProfile(
            @Parameter(description = "조회할 회원의 ID", required = true, example = "1")
            @RequestParam Long targetMemberId
    );
}
