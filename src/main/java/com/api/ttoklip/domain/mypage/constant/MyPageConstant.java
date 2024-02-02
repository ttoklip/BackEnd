package com.api.ttoklip.domain.mypage.constant;

public class MyPageConstant {

    public static final String myPageResponse = """
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

    public static final String termsAndPolicyResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "terms": "이용약관 내용 예시",
                "privacyPolicy": "개인정보 처리방침 내용 예시",
                "cookiePolicy": "쿠키 정책 내용 예시"
            }
        }
        """;
    public static final String restrictedResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "restricted": {
                    "type": "이용 제재",
                    "duration": "30일",
                    "reason": "부적절한 행동"
                }
            }
        }
        """;
    public static final String blockedUsersResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "blockedUsers": [
                    {
                        "userId": "사용자1",
                        "reason": "부적절한 행동"
                    },
                    {
                        "userId": "사용자2",
                        "reason": "스팸 활동"
                    }
                ]
            }
        }
        """;
    public static final String unblockUserResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "사용자의 차단이 성공적으로 해제되었습니다."
        }
        """;
    public static final String scrapedPostsResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "scrapedPosts": [
                    {
                        "postId": 1,
                        "type": "honeytip",
                        "title": "스크랩한 허니팁 제목 예시 1",
                        "content": "스크랩한 허니팁 내용 예시 1",
                        "author": "글쓴이1",
                        "category": "카테고리1",
                        "scrapedTime": "2024-01-11T16:06:30.852Z",
                        "likes": 10,
                        "comments": 3,
                        "scrapCount": 20
                    },
                    {
                        "postId": 2,
                        "type": "newsletter",
                        "title": "스크랩한 뉴스레터 제목 예시 2",
                        "content": "스크랩한 뉴스레터 내용 예시 2",
                        "author": "글쓴이2",
                        "category": "카테고리2",
                        "scrapedTime": "2024-01-10T10:30:00.000Z",
                        "likes": 15,
                        "comments": 5,
                        "scrapCount": 30
                    },
                    {
                        "postId": 3,
                        "type": "cart",
                        "title": "스크랩한 카트 제목 예시 3",
                        "content": "스크랩한 카트 내용 예시 3",
                        "author": "글쓴이3",
                        "category": "카테고리3",
                        "scrapedTime": "2024-01-09T12:00:00.000Z",
                        "likes": 8,
                        "comments": 2,
                        "scrapCount": 25
                    }
                ]
            }
        }
        """;
    public static final String myPostsResponse = """
        {
            "time": "2024-01-11T16:06:30.852Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "myPosts": [
                    {
                        "postId": 1,
                        "type": "honeytip",
                        "title": "내가 쓴 허니팁 제목 예시 1",
                        "content": "내가 쓴 허니팁 내용 예시 1",
                        "category": "카테고리1",
                        "postedTime": "2024-01-11T16:06:30.852Z",
                        "likes": 15,
                        "comments": 7,
                        "scrapCount": 30
                    },
                    {
                        "postId": 2,
                        "type": "newsletter",
                        "title": "내가 쓴 뉴스레터 제목 예시 2",
                        "content": "내가 쓴 뉴스레터 내용 예시 2",
                        "category": "카테고리2",
                        "postedTime": "2024-01-10T10:30:00.000Z",
                        "likes": 20,
                        "comments": 10,
                        "scrapCount": 40
                    },
                    {
                        "postId": 3,
                        "type": "cart",
                        "title": "내가 쓴 카트 제목 예시 3",
                        "content": "내가 쓴 카트 내용 예시 3",
                        "category": "카테고리3",
                        "postedTime": "2024-01-09T12:00:00.000Z",
                        "likes": 10,
                        "comments": 5,
                        "scrapCount": 20
                    }
                ]
            }
        }
        """;





}
