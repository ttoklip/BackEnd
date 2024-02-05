package com.api.ttoklip.domain.mypage.term.constant;

public class TermConstant {
    public static final String termsAndPolicyListResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "terms": "이용약관 내용 예시",
                "privacyPolicy": "개인정보 처리방침 내용 예시",
                "cookiePolicy": "쿠키 정책 내용 예시"
            }
        }
        """;
    public static final String updateTermsAndPolicy= """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "이용약관이 성공적으로 수정되었습니다."
            }
                     
            """;

    public static final String createTermAndPolicy= """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "약관이 성공적으로 등록되었습니다.",
                "result": {
                    "termsId": 12345,
                    "termsVersion": "v1.0",
                    "createdAt": "2024-01-11T16:06:30.852Z"
                }
            }
                        
            """;
    public static final String termsAndPolicyResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "약관 조회에 성공하였습니다.",
                "result": {
                    "termsId": 12345,
                    "termsVersion": "v1.0",
                    "content": "특정 약관 내용 예시",
                    "createdAt": "2024-01-11T16:06:30.852Z"
                }
            }
                        
            """;

}
