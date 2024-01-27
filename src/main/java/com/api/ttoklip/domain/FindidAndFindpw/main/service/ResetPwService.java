package com.api.ttoklip.domain.FindidAndFindpw.main.service;

import com.api.ttoklip.domain.FindidAndFindpw.main.dto.request.ResetPwRequest;
import com.api.ttoklip.domain.FindidAndFindpw.main.dto.response.ResetPwResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResetPwService {
    public ResetPwResponse resetPassword(String tempToken, @RequestBody ResetPwRequest resetPwRequest){
        return null;
    }
    public Long requestEmailVerification(String email){//추가 구현 필요
        return null;
    }
    public String verifyEmail(Long emailVerifyNum){//추가 구현 필요
        return null;
    }
}
