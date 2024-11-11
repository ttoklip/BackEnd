package com.batch.member;

import com.domain.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberService memberService;

    @Transactional
    @Scheduled(cron = "5 0 0 * * *", zone = "Asia/Seoul")
    public void updateMemberIndependenceDate() {
        memberService.updateMemberIndependenceDates();
    }

}
