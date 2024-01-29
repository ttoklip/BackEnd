package com.api.ttoklip.domain.SignUp.main.dto.request;

import com.api.ttoklip.domain.SignUp.image.dto.response.ImageResponse;
import com.api.ttoklip.domain.common.Category;
import lombok.Getter;


@Getter
public class SignUpCondition {
    private String userName;
    private String userBirth;
    private String userEmail;
    private String userAccount;
    private String userPassword;
    private boolean termAgree;
    private boolean selectionTermAgree;
    private String userNickName;
    private String userArea;
    private String independencePeriod;
    private Category categories;
    private ImageResponse imageUrl;
}
