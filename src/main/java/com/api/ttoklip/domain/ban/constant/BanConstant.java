package com.api.ttoklip.domain.ban.constant;

public class BanConstant {
    public static final String banUsersResponse = """
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
    public static final String userBanResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "사용자의 차단을 성공하였습니다."
            }
            """;
    public static final String deleteBanUserResponse = """
            {
                "time": "2024-01-11T16:06:30.852Z",
                "status": 200,
                "code": "200",
                "message": "사용자의 차단이 성공적으로 해제되었습니다."
            }
            """;
}
