package com.api.ttoklip.domain.SignUp.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {
    private String message;
    private long userId;
    private String userName;
    private String userEmail;
    private String userNickName;
}
