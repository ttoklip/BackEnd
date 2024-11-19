package com.api.auth.oauth2.presentation;

import com.api.global.support.response.TtoklipResponse;
import com.api.profile.presentation.PrivacyConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/oauth")
public interface OAuthControllerDocs {

    @Operation(summary = "Server 자체 로그인 API", description = "oauth accessToken으로 로그인을 처리하는 API입니다.", tags = {"Authentication"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "oauth accessToken으로 로그인",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TtoklipResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = PrivacyConstant.LOGIN_SUCCESS,
                                    description = "로그인 성공 예시"
                            )))})

    @PostMapping
    TtoklipResponse<OAuthLoginResponse> login(@RequestBody OAuthLogin request);
}
