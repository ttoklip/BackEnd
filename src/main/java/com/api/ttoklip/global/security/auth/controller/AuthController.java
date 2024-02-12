package com.api.ttoklip.global.security.auth.controller;

import com.api.ttoklip.global.security.auth.dto.LoginRequest;
import com.api.ttoklip.global.security.auth.dto.LoginResponse;
import com.api.ttoklip.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping
    public SuccessResponse<LoginResponse> loginWithKakao(@RequestBody LoginRequest request) {

        return null;
    }
}
