package com.batch.member;

import com.batch.annotation.BatchService;
import com.batch.member.event.UpdateIndependenceDateEvent;
import com.domain.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@BatchService
@RequiredArgsConstructor
public class MemberBatchService {

    private final MemberService memberService;

    @Transactional
    @EventListener(UpdateIndependenceDateEvent.class)
    public void updateMemberIndependenceDate() {
        memberService.updateMemberIndependenceDates();
    }
}
