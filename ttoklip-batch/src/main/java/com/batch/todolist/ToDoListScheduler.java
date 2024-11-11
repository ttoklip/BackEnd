package com.batch.todolist;

import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.todolist.application.TodayToDoListService;
import com.domain.todolist.domain.TodayToDoList;
import com.domain.todolist.domain.vo.ToDoList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ToDoListScheduler {
    private final TodayToDoListService todayToDoListService;
    private final MemberService memberService;

    private static final String SEOUL = "Asia/Seoul";

    @Transactional
    @Scheduled(cron = "21 0 0 * * *", zone = SEOUL)
    public void generateTodayToDoList() {
        List<Member> allMembers = memberService.findAll();

        List<TodayToDoList> todayToDoLists = allMembers.stream()
                .map(member -> TodayToDoList.of(member, ToDoList.getRandomToDoList())
                ).toList();

        todayToDoListService.saveAll(todayToDoLists);
    }

}
