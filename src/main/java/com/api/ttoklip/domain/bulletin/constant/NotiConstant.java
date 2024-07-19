package com.api.ttoklip.domain.bulletin.constant;

public class NotiConstant {
    public static final String noticeResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result":  {
                         "notices": [
                             {
                                 "noticeId": 5,
                                 "title": "first",
                                 "content": "first"
                             },
                             {
                                 "noticeId": 4,
                                 "title": "first",
                                 "content": "fff"
                             },
                             {
                                 "noticeId": 3,
                                 "title": "first",
                                 "content": "fff"
                             },
                             {
                                 "noticeId": 2,
                                 "title": "first",
                                 "content": "fff"
                             },
                             {
                                 "noticeId": 1,
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
    public static final String createNoticeResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Notice의 1번째 게시글을(를) 생성했습니다."
                }
            }
            """;
    public static final String updateNoticeResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Notice의 1번째 게시글을(를) 수정했습니다."
                }
            }
            """;
    public static final String deleteNoticeResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "message": "Notice의 1번째 게시글을(를) 삭제했습니다."
                }
            }
            """;
    public static final String singleNoticeResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "notice": {
                    "noticeId": 1,
                    "title": "첫 번째 공지사항",
                    "content": "이것은 첫 번째 공지사항의 내용입니다.",
                    "createdAt": "24.02.09 23:08"
                }
            }
            """;


}
