package com.api.ttoklip.domain.auth.constant;

public class AuthRespoonseConstant {
    public static final String signup = """
            {
                "time": "2024-02-06T12:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "회원가입에 성공하였습니다.",
                "result": {
                    "userId": 1
                }
            }
            """;

    public static final String signIn = """
            {
                "time": "2024-02-06T12:30:00.000Z",
                "status": 200,
                "code": "200",
                "message": "로그인에 성공하였습니다.",
                "result": {
                    "userId": 1,
                    "token": "abcdef12345"
                }
            }
            """;

    public static final String checkNickname = """
            {
                "time": "2024-02-06T13:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "닉네임이 사용 가능합니다.",
                "result": {
                    "isAvailable": true
                }
            }
            """;
    public static final String whoAmI = """
            {
                "time": "2024-02-06T13:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "사용자 정보를 확인합니다.",
                "result": {
                    "isAvailable": true
                }
            }
            """;
}
