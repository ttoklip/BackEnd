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

    public static final String inquirySingleResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "문의 조회에 성공하였습니다.",
                "result": {
                    "inquiryId": 123,
                    "title": "문의 제목",
                    "content": "문의 내용",
                    "createdBy": "사용자 이름",
                    "createdAt": "2024-01-11T10:30:00Z"
                }
            }
                        
            """;

    public static final String inquiryListResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "문의 목록 조회에 성공하였습니다.",
                "result": {
                    "inquiries": [
                        {
                            "inquiryId": 123,
                            "title": "문의 제목 1",
                            "content": "문의 내용 1",
                            "createdBy": "사용자 이름 1",
                            "createdAt": "2024-01-11T10:30:00Z"
                        },
                        {
                            "inquiryId": 124,
                            "title": "문의 제목 2",
                            "content": "문의 내용 2",
                            "createdBy": "사용자 이름 2",
                            "createdAt": "2024-01-12T08:45:00Z"
                        },
                        ...
                    ]
                }
            }
                        
            """;
}
