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
    public static final String faqRegisterResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Faq의 1번째 게시글을(를) 생성했습니다."
                }
            }
                        
            """;
    public static final String inquiryRegisterResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Inquiry의 1번째 게시글을(를) 생성했습니다."
                }
            }
                        
            """;

    public static final String inquiryDeleteResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Inquiry의 1번째 게시글을(를) 삭제했습니다."
                }
            }
                        
            """;

    public static final String inquirySingleResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "inquiryId": 123,
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
                            "content": "문의 내용 1",
                            "createdBy": "사용자 이름 1",
                            "createdAt": "2024-01-11T10:30:00Z"
                        },
                        {
                            "inquiryId": 124,
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
