package com.api.ttoklip.domain.home.service;

import static com.api.ttoklip.global.util.SecurityUtil.getCurrentMember;

import com.api.ttoklip.domain.home.response.HomeResponse;
import com.api.ttoklip.domain.honeytip.post.service.HoneyTipPostService;
import com.api.ttoklip.domain.main.dto.response.TitleResponse;
import com.api.ttoklip.domain.newsletter.main.dto.response.NewsletterThumbnailResponse;
import com.api.ttoklip.domain.newsletter.post.service.NewsletterPostService;
import com.api.ttoklip.domain.todolist.domain.TodayToDoList;
import com.api.ttoklip.domain.todolist.domain.TodayToDoListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final HoneyTipPostService honeyTipPostService;
    private final NewsletterPostService newsletterPostService;
    private final WeatherService weatherService;
    private final TodayToDoListRepository todayToDoListRepository;

    @Transactional
    public HomeResponse home() {
        // ToDo 함께해요, 날씨 api, 오늘의 투두리스트 추후에
        List<TitleResponse> honeyTipRecent3 = honeyTipPostService.getRecent3();
        List<NewsletterThumbnailResponse> newsletterRecent3 = newsletterPostService.getRecent3();

        TodayToDoList todayToDoList = todayToDoListRepository.findTodayToDoListsByMemberId(
                getCurrentMember().getId());

//        weatherService.getWeather();
        return HomeResponse.builder()
                .honeyTips(honeyTipRecent3)
                .newsLetters(newsletterRecent3)
                .currentMemberNickname(getCurrentMember().getNickname())
                .todayToDoList(todayToDoList.getToDoList().getDescription())
//                .street(getCurrentMember().get)
//                .weather()
                .build();

    }
}
