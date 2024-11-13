package com.api.town.presentation;

public class TownResponseConstant {

    public static final String townValue = """
            {
              "time": "2024-01-11T16:06:30.852Z",
              "status": 200,
              "code": "200",
              "message": "요청에 성공하였습니다.",
              "result": {
                "searchData": [
                  {
                    "cartId": 1,
                    "title": "함께해요 제목 예시 1",
                    "content": "함께해요 내용 예시 1",
                    "writer": "작성자 예시 1",
                    "commentCount": 3,
                    "writtenTime": "24.01.25 15:00",
                  },
                  {
                    "questionId": 2,
                    "title": "함께해요 제목 예시 2",
                    "content": "함께해요 내용 예시 2",
                    "writer": "작성자 예시 2",
                    "commentCount": 5,
                    "writtenTime": "24.01.26 10:30",
                  }
                ],
                "totalPage": 10,
                "totalElements": 100,
                "isFirst": true,
                "isLast": false
              }
            }
            """;

    public static final String listTowns = """
            {
                "time": "2024-02-07T10:10:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": [
                    {
                        "TownId": 3,
                        "title": "리스트 우리동네 제목 예시 1",
                        "location": "리스트 우리동네 거래희망장소 예시 1",
                        "author": "작성자3",
                        "publicationDate": "24.02.07 17:00"
                    },
                    {
                        "newsletterId": 4,
                        "title": "리스트 뉴스레터 제목 예시 2",
                        "location": "리스트 뉴스레터 거래희망장소 예시 2",
                        "author": "작성자4",
                        "publicationDate": "24.02.08 07:45"
                    }
                ]
            }
            """;

    public static final String getCommunities = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "communities": [
                                 {
                                     "communityId":5,
                                     "title": "first",
                                     "content": "first"
                                 },
                                 {
                                     "communityId": 4,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "communityId": 3,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "communityId": 2,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "communityId": 1,
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

    public static final String getCarts = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "communities": [
                                 {
                                     "communityId":5,
                                     "title": "first",
                                     "content": "first"
                                 },
                                 {
                                     "communityId": 4,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "communityId": 3,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "communityId": 2,
                                     "title": "first",
                                     "content": "fff"
                                 },
                                 {
                                     "communityId": 1,
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

    public static final String getRecent3 = """ 
            {
              "time": "2024-07-29T20:42:52.316866",
              "status": 200,
              "code": "200",
              "message": "요청에 성공하였습니다.",
              "result": {
                "communityRecent3": [
                  {
                    "communityId": 15,
                    "title": "소통해요 제목 예시1",
                    "street": "서울"
                  },
                  {
                    "communityId": 14,
                    "title": "소통해요 제목 예시2",
                    "street": "경기도 안양시 동안구 비산동  1185"
                  },
                  {
                    "communityId": 13,
                    "title": "소통해요 제목 예시3",
                    "street": "경기도 안양시 동안구 비산동  1185"
                  }
                ],
                "cartRecent3": [
                  {
                    "id": 13,
                    "title": "함께해요 제목 예시1",
                    "location": "경기도 안양시 동안구 비산동  1185"
                  },
                  {
                    "id": 25,
                    "title": "함께해요 제목 예시2",
                    "location": "Road, Mountain View,"
                  },
                  {
                    "id": 24,
                    "title": "함께해요 제목 예시3",
                    "location": "서울특별시 송파구 문정로"
                  }
                ]
              }
            }
            """;
}
