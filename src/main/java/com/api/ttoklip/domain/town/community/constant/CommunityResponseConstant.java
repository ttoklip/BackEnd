package com.api.ttoklip.domain.town.community.constant;

public class CommunityResponseConstant {
    public static final String createAndDeleteCommunity = """
            {
                "time": "2024-01-30T10:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "communityId": 1,
                    "title": "소통해요 게시글 제목 예시",
                    "content": "소통해요 게시글 내용 예시",
                    "author": "작성자 예시",
                    "writtenTime": "24.01.29 11:00"
                }
            }
            """;

    public static final String readSingleCommunity = """
            {
                "time": "2024-01-30T10:05:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "communityId": 2,
                    "title": "개별 소통해요 게시글 제목 예시",
                    "content": "개별 소통해요 게시글 내용 예시",
                    "author": "작성자2",
                    "writtenTime": "24.01.30 11:30"
                },
                "imageUrls": [
                  {
                    "imageId": 101,
                    "postImageUrl": "http://example.com/image1.jpg"
                  },
                  {
                    "imageId": 102,
                    "postImageUrl": "http://example.com/image2.jpg"
                  }
                ]
            }
            """;

    public static final String updateCommunity = """
            {
                "time": "2024-01-30T12:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "communityId": 3,
                    "title": "수정된 게시글 제목 예시",
                    "content": "수정된 게시글 내용 예시",
                    "author": "수정된 작성자 예시",
                    "writtenTime": "24.01.30 12:00"
                }
            }
            """;

    public static final String createLike = """
        {
            "time": "2024-01-30T12:00:00.000Z",
            "status": 200,
            "code": "200",
            "message": "요청에 성공하였습니다.",
            "result": {
                "likeId": 1,
                "memberId": 3,
                "communityId": 5,
                "likedTime": "24.02.08 12:00"
            }
        }
        """;
}
