package com.api.ttoklip.domain.home.service;

import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.newsletter.post.service.NewsletterPostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final HoneyTipPostService honeyTipPostService;


    public void home() {
        // ToDo 함께해요는 추후에

        List<TitleResponse> honeyTipRecent3 = honeyTipPostService.getRecent3();

//        HomeResponse.builder()
//                .honeyTips(honeyTipRecent3)
//                .newsLetters()
//                .currentMemberNickname(getCurrentMember().getNickname())
//                .street()
//                .weather()
//                .build()

    }
}
