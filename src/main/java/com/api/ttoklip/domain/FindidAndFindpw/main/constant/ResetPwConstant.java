package com.api.ttoklip.domain.FindidAndFindpw.main.constant;

public class ResetPwConstant {
    public static final String resetPasswordSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "비밀번호 재설정 성공",
            "result": null
        }
        """;

    public static final String requestSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "이메일 인증번호 요청 성공",
            "result": {
                "verificationCode": "123456"
            }
        }
        """;


    public static final String verificationSuccessResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "이메일 인증번호 확인 성공",
            "result": {
                "verificationStatus": true
            }
        }
        """;

}
