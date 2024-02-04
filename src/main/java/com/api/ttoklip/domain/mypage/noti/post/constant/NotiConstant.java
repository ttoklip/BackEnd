package com.api.ttoklip.domain.mypage.noti.post.constant;

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
    public static final String createNoticeResponse = """
    {
        "time": "2024-01-11T16:06:30.852Z",
        "status": 201,
        "code": "201",
        "message": "공지사항이 성공적으로 작성되었습니다."
    }
    """;
    public static final String updateNoticeResponse = """
    {
        "time": "2024-01-11T16:06:30.852Z",
        "status": 200,
        "code": "200",
        "message": "공지사항이 성공적으로 수정되었습니다."
    }
    """;
    public static final String deleteNoticeResponse = """
    {
        "time": "2024-01-11T16:06:30.852Z",
        "status": 200,
        "code": "200",
        "message": "공지사항이 성공적으로 삭제되었습니다."
    }
    """;
    public static final String singleNoticeResponse = """
    {
        "time": "2024-01-11T16:06:30.852Z",
        "status": 200,
        "code": "200",
        "message": "공지사항 조회가 성공적으로 완료되었습니다.",
        "notice": {
            "noticeId": 1,
            "title": "첫 번째 공지사항",
            "content": "이것은 첫 번째 공지사항의 내용입니다.",
            "createdAt": "2024-01-11T10:00:00.000Z"
        }
    }
    """;


}
