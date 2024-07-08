package com.api.ttoklip.global.security.auth.controller;

import com.api.ttoklip.global.security.auth.dto.request.AuthLoginRequest;
import com.api.ttoklip.global.security.auth.dto.request.AuthRequest;
import com.api.ttoklip.global.security.auth.dto.response.AuthLoginResponse;
import com.api.ttoklip.global.security.auth.service.AuthService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입/로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    /* --------------------------------- signup --------------------------------- */
    @Operation(summary = "회원가입", description = "직접 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @PostMapping("/signup")
    public SuccessResponse<Message> signup(final @Validated @ModelAttribute AuthRequest request) {
        System.out.println("request = " + request);
        return new SuccessResponse<>(authService.signup(request));
    }

    /* --------------------------------- duplicate --------------------------------- */
    @Operation(summary = "중복확인", description = "아이디 중복확인을 진행합니다..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @PostMapping("/duplicate")
    public SuccessResponse<Message> duplicate(@RequestParam String newId) {
        return new SuccessResponse<>(authService.duplicate(newId));
    }

    /* --------------------------------- login --------------------------------- */
    @Operation(summary = "로그인", description = "직접 로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    @PostMapping("/login")
    public SuccessResponse<AuthLoginResponse> login(@RequestBody AuthLoginRequest authLoginRequest) {
        AuthLoginResponse authLoginResponse = authService.login(authLoginRequest);
        return new SuccessResponse<>(authLoginResponse);
    }

}
