package com.domain.todolist.domain;

import java.util.List;

public interface TodayToDoListRepository {
    void saveAll(List<TodayToDoList> todayToDoLists);

    TodayToDoList findTodayToDoListsByMemberId(Long memberId);
}
