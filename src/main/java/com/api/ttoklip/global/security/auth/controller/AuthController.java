package com.api.ttoklip.global.security.auth.controller;

import com.api.ttoklip.global.security.auth.dto.LoginRequest;
import com.api.ttoklip.global.security.auth.dto.LoginResponse;
import com.api.ttoklip.global.security.auth.service.AuthService;
import com.api.ttoklip.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public SuccessResponse<LoginResponse> login(final @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.authenticate(request);
        return new SuccessResponse<>(loginResponse);
    }
}
