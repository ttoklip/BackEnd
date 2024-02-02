package com.api.ttoklip.domain.mypage.service;

import com.api.ttoklip.domain.mypage.dto.request.MyPageRequest;
import com.api.ttoklip.domain.mypage.dto.response.MyPageResponse;
import com.api.ttoklip.domain.mypage.dto.response.NoticeListResponse;
import com.api.ttoklip.domain.mypage.dto.response.NoticeResponse;
import com.api.ttoklip.domain.mypage.dto.response.TermsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    public MyPageResponse getMyProfile(MyPageRequest myPageRequest){return null;}
    public NoticeListResponse noticeList(){
        return null;
    }
    public TermsResponse term(String termType){
        return null;
    }
}
