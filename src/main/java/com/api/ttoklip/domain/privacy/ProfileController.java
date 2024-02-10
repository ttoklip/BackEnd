package com.api.ttoklip.domain.privacy;

import com.api.ttoklip.domain.privacy.dto.PrivacyCreateRequest;
import com.api.ttoklip.domain.privacy.service.ProfileService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AFTER AUTH LOGIN API", description = "꿀팁공유해요 소셜 로그인 후 회원가입 API입니다.")
@RestController
@RequestMapping("/api/v1/privacy")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/insert")
    public SuccessResponse<Long> register(@RequestBody @Validated final PrivacyCreateRequest request) {
        profileService.insert(request);

        return new SuccessResponse<>(1000L);
    }
}
