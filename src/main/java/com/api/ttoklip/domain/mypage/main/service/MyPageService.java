package com.api.ttoklip.domain.mypage.main.service;


import com.api.ttoklip.domain.mypage.main.dto.request.BlockedRequest;
import com.api.ttoklip.domain.mypage.main.dto.request.MyPageRequest;
import com.api.ttoklip.domain.mypage.main.dto.response.*;
import com.api.ttoklip.global.success.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    public MyPageResponse getMyProfile(){return null;}
    public BlockedListResponse blockedUser(){
        return null;
    }
    public RestricetdResponse restricted(){
        return null;
    }
    public Message unblock(String blockedUserId){
        return null;
    }
    public ScrapPostsListResponse scrapPosts(){
        return null;
    }
    public MyPostsListResponse myPosts(){
        return null;
    }
    public ParticipateListResponse participateDeals(){
        return null;
    }
}
