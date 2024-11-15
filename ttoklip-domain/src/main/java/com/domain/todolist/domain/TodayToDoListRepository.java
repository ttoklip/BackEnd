package com.domain.todolist.domain;

import com.domain.member.domain.Member;
import java.util.List;

public interface TodayToDoListRepository {
    void saveAll(List<TodayToDoList> todayToDoLists);

    TodayToDoList findTodayToDoListsByMember(Member member);
}
