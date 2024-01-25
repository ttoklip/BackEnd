package com.api.ttoklip.domain.FindidAndFindpw.main.controller;

import com.api.ttoklip.domain.FindidAndFindpw.main.constant.FindIdConstant;
import com.api.ttoklip.domain.FindidAndFindpw.main.dto.request.FindIdRequest;
import com.api.ttoklip.domain.FindidAndFindpw.main.dto.response.FindIdResponse;
import com.api.ttoklip.domain.FindidAndFindpw.main.service.FindIdService;
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

@Tag(name = "Find Id", description = "FindId API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/find")
public class FindIdController {
    private final FindIdService FindIdService;
    @Operation(summary = "아이디 찾기 API",
            description = "이메일을 이용하여 아이디를 찾고 결과를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 찾기 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = FindIdConstant.findUserAuthSuccessResponse,
                                    description = "아이디 찾기 성공 시 응답"
                            ))),
            @ApiResponse(responseCode = "404", description = "아이디 찾기 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = FindIdConstant.findUserAuthFailureResponse,
                                    description = "아이디 찾기 실패 시 응답"
                            )))})
    @GetMapping("/finduserauth")
    public SuccessResponse<FindIdResponse> findUserAuth(@RequestParam String email) {
        // 아이디 찾기 로직을 여기에 추가
        // 성공하면 성공 응답을, 실패하면 실패 응답을 반환
        FindIdResponse userAuth=FindIdService.findAuthByEmail(email);
        if(userAuth!=null){
            return new SuccessResponse<>(userAuth);
        }
        else{
            return new SuccessResponse<>(null);
        }
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
                                    value = FindIdConstant.requestSuccessResponse,
                                    description = "인증번호 요청 성공 시 응답"
                            ))),
            @ApiResponse(responseCode = "400", description = "인증번호 요청 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = FindIdConstant.requestFailureResponse,
                                    description = "인증번호 요청 실패 시 응답"
                            )))})
    @PostMapping("/emailrequest")
    public SuccessResponse<Long> requestEmailVerification(@RequestBody String email) {
        // 이메일 인증번호 요청 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        return new SuccessResponse<>(FindIdService.requestEmailVerification(email));
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
                                    value = FindIdConstant.verificationSuccessResponse,
                                    description = "인증번호 요청 성공 시 응답"
                            ))),
            @ApiResponse(responseCode = "400", description = "인증번호 요청 실패",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    name = "SuccessResponse",
                                    value = FindIdConstant.verificationFailureResponse,
                                    description = "인증번호 요청 실패 시 응답"
                            )))})
    @PostMapping("/emailverification")
    public SuccessResponse<String> verifyEmail(@RequestBody Long emailVerifyNum) {
        // 이메일 인증번호 요청 로직을 수행하여 결과를 반환하는 서비스 메서드를 호출
        boolean verifyEmail= FindIdService.verifyEmail(emailVerifyNum);
        if(verifyEmail){
            return new SuccessResponse<>("이메일 인증 성공");
        }else{
            return new SuccessResponse<>("이메일 인증 실패");
        }
    }

}
