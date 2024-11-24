package com.api.auth.local.presentation;

import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.term.response.TermSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth", description = "회원가입/로그인 관련 API")
public interface LocalAuthControllerDocs {

    @Operation(summary = "회원가입", description = "직접 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    TtoklipResponse<Message> signup(@Validated @ModelAttribute LocalMemberWebCreate request);

    @Operation(summary = "중복확인", description = "아이디 중복확인을 진행합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    TtoklipResponse<Message> duplicate(@RequestParam String newId);

    @Operation(summary = "로그인", description = "직접 로그인을 진행합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    TtoklipResponse<AuthLoginResponse> login(@RequestBody AuthLogin authLogin);

    @Operation(summary = "회원가입 전용 이용약관 조회", description = "회원가입 전 이용약관을 조회하는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이용약관 조회 성공")
    })
    TtoklipResponse<TermSignUpResponse> getTermSignUp();
}
