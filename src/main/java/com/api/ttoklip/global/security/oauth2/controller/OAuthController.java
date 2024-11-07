package com.api.ttoklip.global.security.oauth2.controller;

import com.api.ttoklip.domain.privacy.constant.PrivacyConstant;
import com.api.ttoklip.global.security.oauth2.dto.OAuthLoginRequest;
import com.api.ttoklip.global.security.oauth2.dto.OAuthLoginResponse;
import com.api.ttoklip.global.security.oauth2.service.OAuthService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class OAuthController {
    private final OAuthService OAuthService;

    @Operation(summary = "Server 자체 로그인", description = "oauth accessToken으로 로그인",
            tags = {"Authentication"}) // 태그 추가
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "oauth accessToken으로 로그인",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = PrivacyConstant.LOGIN_SUCCESS,
                                    description = "로그인"
                            )))})
    @PostMapping
    public SuccessResponse<OAuthLoginResponse> login(final @RequestBody OAuthLoginRequest request) {
        OAuthLoginResponse OAuthLoginResponse = OAuthService.authenticate(request);
        return new SuccessResponse<>(OAuthLoginResponse);
    }
}
