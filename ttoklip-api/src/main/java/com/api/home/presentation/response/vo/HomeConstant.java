package com.api.home.presentation.response.vo;

public class HomeConstant {
    public static final String HOME_RESPONSE = """
            {
                "time": "2024-02-20T02:50:09.793733",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "currentMemberNickname": "수박",
                    "street": "서울특별시 관악구 조원로12길",
                    "todayToDoList": "오늘은 이불빨래 하는 날!",
                    "honeyTips": [
                        {
                            "id": 25,
                            "title": "12",
                            "content": "12",
                            "writer": "플램",
                            "likeCount": 0,
                            "commentCount": 0,
                            "scrapCount": 0,
                            "writtenTime": "24.02.20 02:11"
                        },
                        {
                            "id": 24,
                            "title": "11",
                            "content": "11",
                            "writer": "플램",
                            "likeCount": 0,
                            "commentCount": 0,
                            "scrapCount": 0,
                            "writtenTime": "24.02.20 02:11"
                        },
                        {
                            "id": 23,
                            "title": "10",
                            "content": "10",
                            "writer": "플램",
                            "likeCount": 0,
                            "commentCount": 0,
                            "scrapCount": 0,
                            "writtenTime": "24.02.20 02:11"
                        }
                    ],
                    "newsLetters": [
                        {
                            "newsletterId": 14,
                            "title": "423232게시글 제목 예시5334577232423",
                            "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/img3.png",
                            "writtenTime": "2024.02.20"
                        },
                        {
                            "newsletterId": 13,
                            "title": "423232게시글 제목 예시5334577232423",
                            "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/img3.png",
                            "writtenTime": "2024.02.20"
                        },
                        {
                            "newsletterId": 12,
                            "title": "423232게시글 제목 예시5334577232423",
                            "mainImageUrl": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/img3.png",
                            "writtenTime": "2024.02.20"
                        }
                    ],
                    "carts": [
                        {
                            "id": 5,
                            "title": "hh",
                            "location": "seoul",
                            "totalPrice": 63000,
                            "partyMax": 2,
                            "writer": "수박",
                            "partyCnt": 2,
                            "commentCount": 0,
                            "currentPrice": 63000,
                            "tradeStatus": "COMPLETED"
                        },
                        {
                            "id": 4,
                            "title": "제목입니다.",
                            "location": "경기도 시시",
                            "totalPrice": 12300,
                            "partyMax": 10,
                            "writer": "수박",
                            "partyCnt": 2,
                            "commentCount": 0,
                            "currentPrice": 2460,
                            "tradeStatus": "IN_PROGRESS"
                        },
                        {
                            "id": 3,
                            "title": "제목입니다.",
                            "location": "경기도 시시",
                            "totalPrice": 12300,
                            "partyMax": 10,
                            "writer": "수박",
                            "partyCnt": 2,
                            "commentCount": 0,
                            "currentPrice": 2460,
                            "tradeStatus": "IN_PROGRESS"
                        }
                    ]
                }
            }
            """;
}
