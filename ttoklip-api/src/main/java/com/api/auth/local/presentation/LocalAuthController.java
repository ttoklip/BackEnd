package com.api.auth.local.presentation;

import com.api.auth.local.application.AuthFacade;
import com.api.global.support.response.Message;
import com.api.global.support.response.TtoklipResponse;
import com.domain.term.response.TermSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
public class LocalAuthController {

    private final AuthFacade authFacade;

    /* --------------------------------- signup --------------------------------- */
    @Operation(summary = "회원가입", description = "직접 회원가입을 진행합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TtoklipResponse<Message> signup(
            final @Validated @ModelAttribute LocalMemberWebCreate request
    ) {
        return new TtoklipResponse<>(authFacade.signup(request));
    }

    /* --------------------------------- duplicate --------------------------------- */
    @Operation(summary = "중복확인", description = "아이디 중복확인을 진행합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원가입 성공")
    })
    @PostMapping("/duplicate")
    public TtoklipResponse<Message> duplicate(final @RequestParam String newId) {
        return new TtoklipResponse<>(authFacade.duplicate(newId));
    }

    /* --------------------------------- login --------------------------------- */
    @Operation(summary = "로그인", description = "직접 로그인을 진행합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    @PostMapping("/login")
    public TtoklipResponse<AuthLoginResponse> login(final @RequestBody AuthLogin authLogin) {
        AuthLoginResponse response = authFacade.login(authLogin);
        return new TtoklipResponse<>(response);
    }

    @Operation(summary = "회원가입 전용 이용약관 조회", description = "회원가입 전 이용약관을 조회하는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "이용약관 조회 성공")
    })
    @GetMapping("/agree")
    public TtoklipResponse<TermSignUpResponse> getTermSignUp() {
        TermSignUpResponse response = authFacade.getTermWhenSignUp();
        return new TtoklipResponse<>(response);
    }
}
