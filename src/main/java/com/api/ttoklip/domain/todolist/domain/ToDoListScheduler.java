package com.api.ttoklip.domain.todolist.domain;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ToDoListScheduler {
    private final JPAQueryFactory queryFactory;
    private final TodayToDoListRepository todayToDoListRepository;

    @Transactional
    // 매일 자정 5분에 실행 (서울 기준)
    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Seoul")
    public void generateTodayToDoList() {

        List<Member> allMembers = queryFactory
                .selectFrom(QMember.member)
                .fetch();

        List<TodayToDoList> todayToDoLists = allMembers.stream()
                .map(member -> TodayToDoList.of(member, ToDoList.getRandomToDoList())
                ).toList();

        todayToDoListRepository.saveAll(todayToDoLists);
    }

}
