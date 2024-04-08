package com.api.ttoklip.domain.join.controller;

import com.api.ttoklip.domain.join.dto.response.LoginResponse;
import com.api.ttoklip.domain.join.service.JoinService;
import com.api.ttoklip.global.exception.ApiExceptionResponse;
import com.api.ttoklip.global.security.auth.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입/로그인 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
@Slf4j
public class JoinController {

    private final JoinService joinService;
    @Operation(summary = "회원가입", description = "직접 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일입니다.",
                    content = @Content
                            (schema = @Schema(implementation = ApiExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "입력하지 않은 요소가 존재합니다.",
                    content = @Content
                            (schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/signup")
    public void signup(@RequestBody RegisterRequest request) {
        joinService.register(request);
    }

    @Operation(summary = "로그인", description = "직접 로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 정보가 일치하지 않는 경우",
                    content = @Content(schema = @Schema(implementation = ApiExceptionResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) throws Exception {
        LoginResponse loginResponse = joinService.login(request, response);
        return ResponseEntity.ok().body(loginResponse);
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
    })
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        joinService.logout(response);
    }
}

// todo 서비스 로직 구현