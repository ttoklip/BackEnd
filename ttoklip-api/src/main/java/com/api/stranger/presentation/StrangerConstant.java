package com.api.stranger.presentation;

public class StrangerConstant {
    public static final String strangerResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "userData": {
                        "nickname": "사용자 닉네임",
                        "residence": "사는 동네",
                        "level": 3,
                        "experience": {
                            "current": 250,
                            "required": 500
                        },
                        "levelimageurl":
                    }
                }
            }
            """;
    public static final String strangerHoneyTipsResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "honeyTips": [
                                 {
                                     "honeyTipId": 5,
                                     "title": "first",
                                     "content": "first"
                                 },
                                 {
                                     "honeyTipId": 4,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "honeyTipId": 3,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "honeyTipId": 2,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "honeyTipId": 1,
                                     "title": "first",
                                     "content": "fff"
                                 }
                             ],
                             "totalPage": 1,
                             "totalElements": 5,
                             "isFirst": true,
                             "isLast": true
                }
            }
            """;
    public static final String participatedDealsResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "participatedDeals": [
                        {
                            "dealId": 1,
                            "title": "참여한 거래 제목 예시 1",
                            "description": "참여한 거래 설명 예시 1",
                            "seller": "판매자1",
                            "price": 10000,
                            "status": "진행 중",
                            "participationTime": "2024-01-11T16:06:30.852Z",
                            "comments": 3,
                            "participantsCount": 5
                        },
                        {
                            "dealId": 2,
                            "title": "참여한 거래 제목 예시 2",
                            "description": "참여한 거래 설명 예시 2",
                            "seller": "판매자2",
                            "price": 20000,
                            "status": "종료",
                            "participationTime": "2024-01-10T10:30:00.000Z",
                            "comments": 5,
                            "participantsCount": 8
                        }
                    ]
                }
            }
            """;
}
