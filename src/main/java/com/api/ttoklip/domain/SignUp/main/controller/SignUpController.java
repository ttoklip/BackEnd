package com.api.ttoklip.domain.SignUp.main.controller;

import com.api.ttoklip.domain.SignUp.main.constant.SignUpResponseConstant;
import com.api.ttoklip.domain.SignUp.main.dto.request.SignUpCondition;
import com.api.ttoklip.domain.SignUp.main.dto.response.SignUpResponse;
import com.api.ttoklip.domain.SignUp.main.service.SignUpService;
import com.api.ttoklip.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SignUp", description = "SignUp API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/signup")
public class SignUpController {

    private final SignUpService SignUpService;

    @Operation(summary = "회원가입 API",
            description = "사용자의 회원가입을 처리하고 결과를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SignUpResponseConstant.successSignUpResponse,
                                    description = "회원가입 성공 시 응답"
                            )))})
    @PostMapping("/signup")
    public SuccessResponse<SignUpResponse> signUp(@RequestBody SignUpCondition signUpCondition) {
        return new SuccessResponse<>(SignUpService.signUp(signUpCondition));
    }

    @Operation(summary = "닉네임 중복 확인 API",
            description = "사용자의 닉네임이 중복되는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복 확인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SignUpResponseConstant.successNickNameResponse,
                                    description = "중복 확인 성공 시 응답"
                            )))})
    @PostMapping("/nicknameduplicatecheck")
    public SuccessResponse<String> checkNickname(@RequestBody String userNickname) {
        // 닉네임 중복 확인 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        String checkNickname= SignUpService.checkNickname(userNickname);
        return new SuccessResponse<>(checkNickname);

    }

    @Operation(summary = "아이디 중복 확인 API",
            description = "사용자의 아이디가 중복되는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복 확인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SignUpResponseConstant.authSuccessResponse,
                                    description = "중복 확인 성공 시 응답"
                            )))})
    @PostMapping("/authduplicatecheck")
    public SuccessResponse<String> checkAuth(@RequestBody String userAuth) {
        // 닉네임 중복 확인 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        String checkAuth=SignUpService.checkAuth(userAuth);
        return new SuccessResponse<>(checkAuth);

    }

    @Operation(summary = "이메일 인증번호 요청 API",
            description = "사용자의 이메일로 인증번호를 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 요청 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SignUpResponseConstant.requestSuccessResponse,
                                    description = "인증번호 요청 성공 시 응답"
                            )))})
    @PostMapping("/emailrequest")
    public SuccessResponse<Long> requestEmailVerification(@RequestBody String userEmail) {
        // 이메일 인증번호 요청 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        // 임시로 작성 후에 구현
        Long requestNum= SignUpService.requestEmailVerification(userEmail);
        return new SuccessResponse<>(requestNum);
    }

    @Operation(summary = "이메일 인증번호 확인",
            description = "인증번호를 검증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 요청 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = SignUpResponseConstant.verificationSuccessResponse,
                                    description = "인증번호 요청 성공 시 응답"
                            )))})
    @PostMapping("/emailverification")
    public SuccessResponse<String> verifyEmail(@RequestBody Long emailVerifyNum) {
        // 이메일 인증번호 요청 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        String result = SignUpService.verifyEmail(emailVerifyNum);
        return new SuccessResponse<>(result);
    }
}

