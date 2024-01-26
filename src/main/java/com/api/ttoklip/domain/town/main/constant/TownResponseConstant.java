package com.api.ttoklip.domain.town.main.constant;

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
}
