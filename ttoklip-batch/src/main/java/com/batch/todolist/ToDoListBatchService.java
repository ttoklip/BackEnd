package com.batch.todolist;

import com.batch.annotation.BatchService;
import com.batch.todolist.event.PickPersonalToDoListEvent;
import com.domain.member.application.MemberService;
import com.domain.member.domain.Member;
import com.domain.todolist.application.TodayToDoListService;
import com.domain.todolist.domain.TodayToDoList;
import com.domain.todolist.domain.vo.ToDoList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@BatchService
@RequiredArgsConstructor
public class ToDoListBatchService {

    private final TodayToDoListService todayToDoListService;
    private final MemberService memberService;

    @Transactional
    @EventListener(PickPersonalToDoListEvent.class)
    public void generateTodayToDoList() {
        List<Member> allMembers = memberService.findAll();

        List<TodayToDoList> todayToDoLists = allMembers.stream()
                .map(member -> TodayToDoList.of(member, ToDoList.getRandomToDoList())
                ).toList();

        todayToDoListService.saveAll(todayToDoLists);
    }
}
