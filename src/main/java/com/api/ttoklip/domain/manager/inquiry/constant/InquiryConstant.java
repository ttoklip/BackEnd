package com.api.ttoklip.domain.manager.inquiry.constant;

public class InquiryConstant {
    public static final String faqResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "자주 물어보는 질문 목록을 성공적으로 가져왔습니다.",
                "result": {
                    "faqList": [
                        {
                            "questionId": 1,
                            "question": "질문 1",
                            "answer": "답변 1"
                        },
                        {
                            "questionId": 2,
                            "question": "질문 2",
                            "answer": "답변 2"
                        },
                        {
                            "questionId": 3,
                            "question": "질문 3",
                            "answer": "답변 3"
                        }
                    ]
                }
            }
                        
            """;
    public static final String inquiryRegisterResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "문의가 성공적으로 전송되었습니다."
            }
                        
            """;
}
