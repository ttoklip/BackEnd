package com.api.profile.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Profile", description = "프로필 관리 API")
@RequestMapping("/api/v1/privacy")
public interface ProfileControllerDocs {

    @Operation(summary = "회원가입 직후 개인정보 입력", description = "프로필 사진, 똑립 전용 닉네임, 자취 경력 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개인정보 입력",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "PrivacyConstant.PROFILE_RESPONSE",
                                    description = "개인정보를 추가했습니다."
                            )))})

    @PostMapping("/insert")
    TtoklipResponse<Message> register(@ModelAttribute @Validated ProfileWebCreate request);

    @Operation(summary = "똑립 oauth 전용 닉네임 중복 확인", description = "oauth 로그인 이후 개인정보 입력 전 닉네임 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "PrivacyConstant.VALIDATE_NICKNAME",
                                    description = "닉네임 중복 통과 여부"
                            )))})

    @GetMapping("/oauth/check-nickname")
    TtoklipResponse<Message> checkOauthNickname(@RequestBody Nickname nickname);

    @Operation(summary = "똑립 local 전용 닉네임 중복 확인", description = "local 회원가입 이후 개인정보 입력 전 닉네임 중복 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = "PrivacyConstant.VALIDATE_NICKNAME",
                                    description = "닉네임 중복 통과 여부"
                            )))})

    @GetMapping("/local/check-nickname")
    TtoklipResponse<Message> checkLocalNickname(@RequestBody Nickname nickname);
}
