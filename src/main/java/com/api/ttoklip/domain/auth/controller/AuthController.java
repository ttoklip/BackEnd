package com.api.ttoklip.domain.auth.controller;

import com.api.ttoklip.domain.auth.constant.AuthRespoonseConstant;
import com.api.ttoklip.domain.auth.dto.request.SignInReq;
import com.api.ttoklip.domain.auth.dto.request.SignUpReq;
import com.api.ttoklip.domain.auth.dto.response.AuthRes;
import com.api.ttoklip.domain.auth.service.AuthService;
import com.api.ttoklip.global.success.Message;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authorization", description = "Authorization API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "유저 회원가입", description = "유저 회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = AuthRespoonseConstant.signup,
                                    description = "회원가입되었습니다."
                            )))})
    @PatchMapping("/sign-up")
    public SuccessResponse<Long> signup(
            @Parameter(description = "Schema의 SignUpReq를 참고해주세요.", required = true) @Valid @RequestBody SignUpReq signUpReq
    ) {
        return new SuccessResponse<>(authService.signUp(signUpReq));
    }


//    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃을 수행합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
//                    content = @Content(
//                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
//                            schema = @Schema(implementation = SuccessResponse.class),
//                            examples = @ExampleObject(
//                                    name = "SuccessResponse",
//                                    value = HoneytipResponseConstant.createAndDeleteHoneytip,
//                                    description = "로그아웃되었습니다."
//                            )))})
//    @PostMapping("/sign-out")
//    public SuccessResponse<Message> signout(
//            @Parameter(description = "AccessToken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
//            ) {
//        return new SuccessResponse<>(authService.signOut(userPrincipal));
//    }

    @Operation(summary = "닉네임 중복 체크", description = "닉네임 중복체크를 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복체크 성공",
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = AuthRespoonseConstant.checkNickname,
                                    description = "중복체크되었습니다."
                            )))})
    @GetMapping("/nicknames/{nickname}")
    public SuccessResponse<Message> checkNickname(
            @Parameter(description = "검사할 닉네임을 입력해주세요.", required = true) @PathVariable(value = "nickname") String nickname
    ) {
        return new SuccessResponse<>(authService.checkNickname(nickname));
    }

}
