package com.api.ttoklip.domain.Login.main.dto.request;

import com.api.ttoklip.domain.Login.main.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SignUpCondition {
    private String userName;
    private String userBirth;
    private String userEmail;
    private boolean emailAuthentication;
    private String userAccount;
    private String userPassword;
    private boolean termAgree;
    private boolean selectionTermAgree;
    private String userNickName;
    private String userArea;
    private String independencePeriod;
    private Category categories;
}
