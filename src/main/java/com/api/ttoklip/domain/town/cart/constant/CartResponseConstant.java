package com.api.ttoklip.domain.town.cart.constant;

public class CartResponseConstant {

    public static final String createAndDeleteCart = """
            {
                "time": "2024-01-30T10:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "cartId": 1,
                    "title": "함께해요 게시글 제목 예시",
                    "content": "함께해요 게시글 내용 예시",
                    "author": "작성자 예시",
                    "writtenTime": "24.01.29 11:00"
                }
            }
            """;

    public static final String readSingleCart = """
            {
                "time": "2024-01-30T10:05:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "cartId": 2,
                    "title": "개별 함께해요 게시글 제목 예시",
                    "content": "개별 함께해요 게시글 내용 예시",
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

    public static final String updateCart = """
            {
                "time": "2024-01-30T12:00:00.000Z",
                "status": 200,
                "code": "200",
                "message": "요청에 성공하였습니다.",
                "result": {
                    "cartId": 3,
                    "title": "수정된 게시글 제목 예시",
                    "content": "수정된 게시글 내용 예시",
                    "author": "수정된 작성자 예시",
                    "writtenTime": "24.01.30 12:00"
                }
            }
            """;

    public static final String REPORT_CART = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "Cart Type의 1번째 게시글을(를) 신고했습니다."
                    }
                }
            """;

    public static final String STATUS_CART = """
                {
                    "time": "2024-02-03T21:26:49.885416",
                    "status": 200,
                    "code": "200",
                    "message": "요청에 성공하였습니다.",
                    "result": {
                        "message": "Cart Type의 상태가 마감되었습니다."
                    }
                }
            """;
}
