package com.api.ttoklip.domain.Login.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String time;
    private int status;
    private String code;
    private String message;
    private LoginResult result;
}
@Getter
@Setter
@AllArgsConstructor
class LoginResult{
    private long userId;
    private String username;
    private String userEmail;
    private String userToken;
}