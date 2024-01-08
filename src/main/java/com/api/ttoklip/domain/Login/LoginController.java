package com.api.ttoklip.domain.Login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Login",description = "Login API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class LoginController {

    @Operation(summary = "로그인",description = "로그인을 합니다")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "로그인에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "로그인에 실패했습니다"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> Login(){
        return null;
    }

    @Operation(summary = "카카오톡 로그인", description = "카카오톡 로그인을 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카카오톡 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "카카오톡 로그인 실패")
    })
    @PostMapping("/kakaologin")
    public ResponseEntity<?> kakaoLogin(){
        return null;
    }

    @Operation(summary = "네이버 로그인", description = "네이버 로그인을 합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "네이버 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "네이버 로그인 실패")
    })
    @PostMapping("/naverlogin")
    public ResponseEntity<?> naverLogin(){
        return null;
    }

    @Operation(summary ="아이디 찾기",description = "아이디를 찾습니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "아이디를 찾았습니다"),
            @ApiResponse(responseCode = "400",description = "아이디가 없습니다")
    })
    @GetMapping("/findid?userAccount=userAccount")
    public ResponseEntity<?> findId(){
        return null;
    }

    @Operation(summary ="회원가입",description = "회원가입을 합니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "회원가입 성공"),
            @ApiResponse(responseCode = "400",description = "회원가입 실패")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(){
        return null;
    }

    @Operation(summary ="id 중복확인",description = "id중복 확인을 합니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "사용 가능한 id"),
            @ApiResponse(responseCode = "400",description = "사용 불 가능한 id")
    })
    @GetMapping("/duplicate/id?userAccount=userAccount")
    public ResponseEntity<?> configDuplicateId(){
        return null;
    }

    @Operation(summary ="닉네임 중복확인",description = "닉네임 중복 확인을 합니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200",description = "사용 가능한 닉네임"),
            @ApiResponse(responseCode = "400",description = "사용 불 가능한 닉네임")
    })
    @GetMapping("/duplicate/nickname?userNickname=userNickname")
    public ResponseEntity<?> configDuplicateNickName(){
        return null;
    }

    @Operation(summary = "이메일 인증 번호 요청",description = "이메일로 인증번호를 받습니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증번호 발급 성공"),
            @ApiResponse(responseCode = "400", description = "인증번호 발급 실패")
    })
    @PostMapping("/requestnum")
    public ResponseEntity<?> requestNum(){
        return null;
    }

    @Operation(summary = "이메일 인증",description = "이메일로 인증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증 성공"),
            @ApiResponse(responseCode = "400", description = "이메일 인증 실패")
    })
    @GetMapping("/certificateEmail")
    public ResponseEntity<?> certificateEmail(){
        return null;
    }

}
