package com.api.ttoklip.domain.FindidAndFindpw.main.service;

import com.api.ttoklip.domain.FindidAndFindpw.main.dto.response.FindIdResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindIdService {
    public FindIdResponse findAuthByEmail(String email){//추가 구현 필요
        return null;
    }
    public Long requestEmailVerification(String email){//추가 구현 필요
        return null;
    }
    public boolean verifyEmail(Long emailVerifyNum){//추가 구현 필요
        return false;
    }
}
