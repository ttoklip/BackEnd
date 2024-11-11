package com.api.profile.presentation;

import com.api.global.success.Message;
import com.api.global.success.SuccessResponse;
import com.api.global.util.SecurityUtil;
import com.api.profile.application.ProfileFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AFTER AUTH LOGIN API", description = "꿀팁공유해요 소셜 로그인 후 회원가입 API입니다.")
@RestController
@RequestMapping("/api/v1/privacy")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileFacade profileFacade;

    @Operation(summary = "회원가입 직후 개인정보 입력", description = "프로필 사진, 똑립 전용 닉네임, 자취 경력 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개인정보 입력",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = PrivacyConstant.PROFILE_RESPONSE,
                                    description = "개인정보를 추가했습니다."
                            )))})
    @PostMapping("/insert")
    public SuccessResponse<Message> register(@ModelAttribute @Validated final ProfileWebCreate request) {
        Long currentMemberId = SecurityUtil.getCurrentMember().getId();
        Message message = profileFacade.insert(request, currentMemberId);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "똑립 oauth 전용 닉네임 중복 확인", description = "oauth 로그인 이후 개인정보 입력 전 닉네임 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = PrivacyConstant.VALIDATE_NICKNAME,
                                    description = "닉네임 중복 통과 여부"
                            )))})
    @GetMapping("/oauth/check-nickname")
    public SuccessResponse<Message> checkOauthNickname(@RequestParam final String nickname) {
        Message message = profileFacade.validNickname(nickname);
        return new SuccessResponse<>(message);
    }

    @Operation(summary = "똑립 local 전용 닉네임 중복 확인", description = "local 회원가입 이후 개인정보 입력 전 닉네임 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = PrivacyConstant.VALIDATE_NICKNAME,
                                    description = "닉네임 중복 통과 여부"
                            )))})
    @GetMapping("/local/check-nickname")
    public SuccessResponse<Message> checkLocalNickname(@RequestParam final String nickname) {
        Message message = profileFacade.validNickname(nickname);
        return new SuccessResponse<>(message);
    }
}
