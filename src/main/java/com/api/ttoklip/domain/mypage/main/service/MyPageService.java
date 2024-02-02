package com.api.ttoklip.domain.mypage.main.service;


import com.api.ttoklip.domain.mypage.main.dto.request.BlockedRequest;
import com.api.ttoklip.domain.mypage.main.dto.request.MyPageRequest;
import com.api.ttoklip.domain.mypage.main.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    public MyPageResponse getMyProfile(MyPageRequest myPageRequest){return null;}
    public BlockedListResponse blockedUser(BlockedRequest blockedRequest){
        return null;
    }
    public RestricetdResponse restricted(Long userID){
        return null;
    }
    public String unblock(String blockedUserId){
        return "해제완료";
    }
    public ScrapPostsListResponse scrapPosts(String type){
        return null;
    }
    public MyPostsListResponse myPosts(){
        return null;
    }
    public ParticipateListResponse participateDeals(){
        return null;
    }
}
