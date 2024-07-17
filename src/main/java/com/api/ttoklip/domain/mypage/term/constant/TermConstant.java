package com.api.ttoklip.domain.mypage.term.constant;

public class TermConstant {
    public static final String termsAndPolicyListResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "terms": [
                         {
                             "termId": 4,
                             "title": "특정 약관",
                             "content": "4번째 약관"
                         },
                         {
                             "termId": 3,
                             "title": "특정 약관",
                             "content": "3번째 약관"
                         },
                         {
                             "termId": 2,
                             "title": "특정 약관",
                             "content": "2번째 약관"
                         },
                         {
                             "termId": 1,
                             "title": "특정 약관",
                             "content": "1번째 약관"
                         }
                     ],
                     "totalPage": 1,
                     "totalElements": 4,
                     "isFirst": true,
                     "isLast": true
                 }
            }
            """;

    public static final String updateTermsAndPolicy = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Term의 1번째 게시글을(를) 수정했습니다."
                }
            }
                     
            """;

    public static final String createTermAndPolicy = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Term의 1번째 게시글을(를) 생성했습니다."
                }
            }
                        
            """;

    public static final String termsAndPolicyResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "termsId": 12345,
                    "title": "특정 약관",
                    "content": "특정 약관 내용 예시",
                    "createdAt": "24.02.12 14:43"
                }
            }
                        
            """;

    public static final String agreeTerm = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "약관에 동의하였습니다."
                }
            }
                        
            """;

}
