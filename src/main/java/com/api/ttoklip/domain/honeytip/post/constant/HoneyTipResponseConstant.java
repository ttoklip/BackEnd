package com.api.ttoklip.domain.honeytip.post.constant;

public class HoneyTipResponseConstant {
    public static final String honeytipValue = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                              "status": 200,
                              "code": "200",
                              "message": "요청에 성공하였습니다.",
                              "result": {
                                "searchData": [
                                  {
                                    "questionId": 1,
                                    "title": "질문 제목 예시 1",
                                    "content": "질문 내용 예시 1",
                                    "writer": "작성자 예시 1",
                                    "commentCount": 3,
                                    "writtenTime": "24.01.10 15:15",
                                    "category": "COOKING"
                                  },
                                  {
                                    "questionId": 2,
                                    "title": "질문 제목 예시 2",
                                    "content": "질문 내용 예시 2",
                                    "writer": "작성자 예시 2",
                                    "commentCount": 5,
                                    "writtenTime": "24.01.11 10:30",
                                    "category": "HOUSEWORK"
                                  }
                                ],
                                "totalPage": 10,
                                "totalElements": 100,
                                "isFirst": true,
                                "isLast": false
                              }
                            }
            """;

    public static final String createAndDeleteHoneytip = """
            {
                                "time": "2024-01-11T16:06:30.852Z",
                                "status": 200,
                                "code": "200",
                                "message": "요청에 성공하였습니다.",
                                "result": {
                                    "categoryResponse": {
                                        "houseworkQuestions": [
                                            {
                                                "questionId": 1,
                                                "title": "집안일 관련 질문 예시",
                                                "content": "집안일 관련 내용 예시",
                                                "writer": "작성자1",
                                                "commentCount": 2,
                                                "writtenTime": "24.01.10 12:00",
                                                "category": "HOUSEWORK"
                                            }
                                        ],
                                        "cookingQuestions": [
                                            {
                                                "questionId": 2,
                                                "title": "요리 관련 질문 예시",
                                                "content": "요리 관련 내용 예시",
                                                "writer": "작성자2",
                                                "commentCount": 3,
                                                "writtenTime": "24.01.11 13:00",
                                                "category": "COOKING"
                                            }
                                        ],
                                        "safeLivingQuestions": [
                                        {
                                                "questionId": 2,
                                                "title": "복지 관련 질문 예시",
                                                "content": "복지 관련 내용 예시",
                                                "writer": "작성자2",
                                                "commentCount": 3,
                                                "writtenTime": "24.01.11 13:00",
                                                "category": "SAFE_LIVING"
                                            }
                                        ],
                                        "welfarePolicyQuestions": [
                                        {
                                                "questionId": 2,
                                                "title": "요리 관련 질문 예시",
                                                "content": "요리 관련 내용 예시",
                                                "writer": "작성자2",
                                                "commentCount": 3,
                                                "writtenTime": "24.01.11 13:00",
                                                "category": "WELFARE_POLICY"
                                        }
                                        ]
                                    },
                                    "topFiveQuestions": [
                                        {
                                            "questionId": 5,
                                            "title": "TOP 5중 1등 게시글 제목 예시",
                                            "content": "TOP 5중 1등 게시글 내용 예시",
                                            "writer": "작성자5",
                                            "commentCount": 5,
                                            "writtenTime": "24.01.13 16:00",
                                            "category": "WELFARE_POLICY"
                                        }
                                    ]
                                }
                            }
            """;

    public static final String CREATE_HONEY_TIP = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "HoneyTip Type의 1번째 게시글을(를) 생성했습니다."
                    }
                }
            """;

    public static final String REPORT_HONEY_TIP = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "HoneyTip Type의 1번째 게시글을(를) 신고했습니다."
                    }
                }
            """;

    public static final String EDIT_HONEY_TIP = """
                {
                    "time": "2024-02-03T21:26:54.063812",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "HoneyTip Type의 1번째 게시글을(를) 수정했습니다."
                    }
                }
            """;

    public static final String DELETE_HONEY_TIP = """
                {
                    "time": "2024-02-03T21:28:04.247031",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "HoneyTip Type의 1번째 게시글을(를) 삭제했습니다."
                    }
                }
            """;
    public static final String REGISTER_LIKE = """
            {
                "time": "2024-02-10T12:55:35.127794",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "HoneyTip Type의 3번째 좋아요을(를) 생성했습니다."
                }
            }
            """;
    public static final String CANCEL = """
            {
                "time": "2024-02-10T13:01:49.26421",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "HoneyTip Type의 3번째 좋아요을(를) 삭제했습니다."
                }
            }
            """;
}
