package com.api.ttoklip.domain.newsletter.main.constant;

public class NewsletterResponseConstant {
    public static final String CREATE_NEWSLETTER = """
            {
                "time": "2024-01-30T10:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Newsletter Type의 1번째 게시글을(를) 생성했습니다."
                }
            }
            """;

    public static final String readSingleNewsletter = """
            {
                "time": "2024-01-30T10:05:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "newsletterId": 2,
                    "title": "개별 뉴스레터 제목 예시",
                    "content": "개별 뉴스레터 내용 예시",
                    "author": "작성자2",
                    "publicationDate": "24.01.30 08:30"
                }
            }
            """;

    public static final String listNewsletters = """
            {
                "time": "2024-01-30T10:10:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": [
                    {
                        "newsletterId": 3,
                        "title": "리스트 뉴스레터 제목 예시 1",
                        "content": "리스트 뉴스레터 내용 예시 1",
                        "author": "작성자3",
                        "publicationDate": "24.01.29 17:00"
                    },
                    {
                        "newsletterId": 4,
                        "title": "리스트 뉴스레터 제목 예시 2",
                        "content": "리스트 뉴스레터 내용 예시 2",
                        "author": "작성자4",
                        "publicationDate": "24.01.30 07:45"
                    }
                ]
            }
            """;

    public static final String CREATE_NEWSLETTER_COMMENT = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "Newsletter Type의 1번째 댓글을(를) 생성했습니다."
                    }
                }
            """;

    public static final String DELETE_NEWSLETTER_COMMENT = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "Newsletter Type의 1번째 댓글을(를) 삭제했습니다."
                    }
                }
            """;

    public static final String REPORT_NEWSLETTER_COMMENT = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "Newsletter Type의 1번째 댓글을(를) 신고했습니다."
                    }
                }
            """;

    public static final String REPORT_NEWSLETTER = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "Newsletter Type의 1번째 게시글을(를) 신고했습니다."
                    }
                }
            """;
}
