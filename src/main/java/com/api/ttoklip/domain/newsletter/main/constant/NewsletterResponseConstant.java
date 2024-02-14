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
                 "time": "2024-02-07T20:06:21.615137",
                 "status": 200,
                 "code": "200",
                 "message": "요청에 성공하였습니다.",
                 "result": {
                     "randomNews": [],
                     "categoryResponses": {
                         "houseWork": [
                             {
                                 "newsletterId": 39,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 38,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 37,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 36,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 35,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 34,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 33,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 32,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 31,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 30,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             }
                         ],
                         "recipe": [
                             {
                                 "newsletterId": 29,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 28,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 27,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 26,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 25,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 24,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 23,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             }
                         ],
                         "safeLiving": [
                             {
                                 "newsletterId": 22,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 21,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 20,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 19,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 18,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 17,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 16,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 15,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 14,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 13,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             }
                         ],
                         "welfarePolicy": [
                             {
                                 "newsletterId": 11,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 10,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 9,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 8,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 7,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 6,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 5,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 4,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 3,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             },
                             {
                                 "newsletterId": 2,
                                 "title": "이건 제몹입니다.",
                                 "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202024-02-05%2015.55.35.png",
                                 "writtenTime": "2024.02.07"
                             }
                         ]
                     }
                 }
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

    public static final String REGISTER_SCRAP = """
            {
                "time": "2024-02-13T12:55:35.127794",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Newsletter Type의 5번째 스크랩을(를) 생성했습니다."
                }
            }
            """;

    public static final String CANCEL_SCRAP = """
            {
                "time": "2024-02-13T13:01:49.26421",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Newsletter Type의 5번째 스크랩을(를) 삭제했습니다."
                }
            }
            """;
}
