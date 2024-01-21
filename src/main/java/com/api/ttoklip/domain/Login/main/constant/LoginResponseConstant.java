package com.api.ttoklip.domain.Login.main.constant;

public class LoginResponseConstant {
    public static final String successResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "로그인 성공",
            "result": {
                "userId": 123,
                "username": "john_doe",
                "email": "john.doe@example.com",
                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
            }
        }
        """;

    public static final String failureResponse = """
        {
            "time": "2024-01-11T16:10:45.124Z",
            "status": 401,
            "code": "401",
            "message": "인증 실패",
            "result": null
        }
        """;

    public static final String kakaoSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "카카오 로그인 성공",
            "result": {
                "userId": "kakao123",
                "userName": "KakaoUser",
                "userEmail": "kakao.user@example.com"
            }
        }
        """;

    public static final String kakaoFailureResponse = """
        {
            "time": "2024-01-11T16:10:45.124Z",
            "status": 401,
            "code": "401",
            "message": "카카오 로그인 실패",
            "result": null
        }
        """;
    public static final String naverSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "카카오 로그인 성공",
            "result": {
                "userId": "kakao123",
                "userName": "KakaoUser",
                "userEmail": "kakao.user@example.com"
            }
        }
        """;

    public static final String naverFailureResponse = """
        {
            "time": "2024-01-11T16:10:45.124Z",
            "status": 401,
            "code": "401",
            "message": "카카오 로그인 실패",
            "result": null
        }
        """;
}
