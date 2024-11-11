package com.api.profile.presentation;

public class PrivacyConstant {
    public static final String PROFILE_RESPONSE = """
            {
                "time": "2024-02-13T04:20:45.021367",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "회원가입 후 개인정보를 추가했습니다."
                }
            }
            """;
    public static final String VALIDATE_NICKNAME = """
            {
                "time": "2024-02-13T04:20:31.659223",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "닉네임 중복 확인에 통과하였습니다."
                }
            }
            """;
    public static final String LOGIN_SUCCESS = """
            {
                "time": "2024-02-13T04:19:22.753484",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "jwtToken": "eyJhbGciiJ9.eyJzdWIiY29tIiwiaWF0IjoxNzA3NzY1NTYyLCJleHAiOjjY0NjJ9.UyT8aH-Wjc2Qx7xBWA",
                    "ifFirstLogin": true
                }
            }
            """;
}
