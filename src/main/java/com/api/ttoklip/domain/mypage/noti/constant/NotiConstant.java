package com.api.ttoklip.domain.mypage.noti.constant;

public class NotiConstant {
    public static final String noticeResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": [
                {
                    "noticeId": 1,
                    "title": "공지사항 제목 예시 1",
                    "content": "공지사항 내용 예시 1",
                    "writtenTime": "2024-01-11T16:06:30.852Z"
                },
                {
                    "noticeId": 2,
                    "title": "공지사항 제목 예시 2",
                    "content": "공지사항 내용 예시 2",
                    "writtenTime": "2024-01-12T10:30:00.000Z"
                }
            ]
        }
        """;
}
