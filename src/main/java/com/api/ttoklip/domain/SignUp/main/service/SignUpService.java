package com.api.ttoklip.domain.SignUp.main.service;

import com.api.ttoklip.domain.SignUp.main.dto.request.SignUpCondition;
import com.api.ttoklip.domain.SignUp.main.dto.response.SignUpResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignUpService {
    public SignUpResponse signUp(SignUpCondition signUpCondition){
        return null;
    }
    public String checkNickname(String userNickname){
        return null;
    }
    public String checkAuth(String userAuth){
        return null;
    }
    public Long requestEmailVerification(String userEmail){
        return null;
    }
    public String verifyEmail(Long emailVerifyNum){
        return null;
    }
}
