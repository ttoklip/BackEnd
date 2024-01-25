package com.api.ttoklip.domain.FindidAndFindpw.main.controller;

import com.api.ttoklip.domain.FindidAndFindpw.main.constant.ResetPwConstant;
import com.api.ttoklip.domain.FindidAndFindpw.main.dto.request.ResetPwRequest;
import com.api.ttoklip.domain.FindidAndFindpw.main.dto.response.ResetPwResponse;
import com.api.ttoklip.domain.FindidAndFindpw.main.service.ResetPwService;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reset Password", description = "ResetPassword API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reset")
public class ResetPwController {

    private ResetPwService resetPwService;
    @Operation(summary = "비밀번호 재설정 API",
            description = "이메일을 이용하여 비밀번호를 재설정하고 결과를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 재설정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = ResetPwConstant.resetPasswordSuccessResponse,
                                    description = "비밀번호 재설정 성공 시 응답"
                            ))),
            @ApiResponse(responseCode = "400", description = "비밀번호 재설정 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = ResetPwConstant.resetPasswordFailureResponse,
                                    description = "비밀번호 재설정 실패 시 응답"
                            )))})
    @PostMapping("/resetpassword")
    public SuccessResponse<ResetPwResponse> resetPassword(@RequestParam String tempToken, @RequestBody ResetPwRequest resetPwRequest) {
        // 비밀번호 재설정 로직을 여기에 추가
        // 성공하면 성공 응답을, 실패하면 실패 응답을 반환
        ResetPwResponse resetPwResponse=resetPwService.resetPassword(tempToken,resetPwRequest);
        return new SuccessResponse<>(resetPwResponse);
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
                                    value = ResetPwConstant.requestSuccessResponse,
                                    description = "인증번호 요청 성공 시 응답"
                            ))),
            @ApiResponse(responseCode = "400", description = "인증번호 요청 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = ResetPwConstant.requestFailureResponse,
                                    description = "인증번호 요청 실패 시 응답"
                            )))})
    @PostMapping("/emailrequest")
    public SuccessResponse<Long> requestEmailVerification(@RequestBody String userEmail) {
        // 이메일 인증번호 요청 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        return new SuccessResponse<>(resetPwService.requestEmailVerification(userEmail));
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
                                    value = ResetPwConstant.verificationSuccessResponse,
                                    description = "인증번호 요청 성공 시 응답"
                            ))),
            @ApiResponse(responseCode = "400", description = "인증번호 요청 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = ResetPwConstant.verificationFailureResponse,
                                    description = "인증번호 요청 실패 시 응답"
                            )))})
    @PostMapping("/emailverification")
    public SuccessResponse<String> verifyEmail(@RequestBody Long emailVerifyNum) {
        // 이메일 인증번호 요청 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        boolean verifyEmail=resetPwService.verifyEmail(emailVerifyNum);
        if (verifyEmail) {
            return new SuccessResponse<>("이메일 인증 완료됐습니다");
        }else{
            return new SuccessResponse<>("이메일 인증 실패했습니다");
        }
    }
}
