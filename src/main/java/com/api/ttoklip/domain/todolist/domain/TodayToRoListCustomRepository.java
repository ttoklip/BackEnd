package com.api.ttoklip.domain.todolist.domain;


public interface TodayToRoListCustomRepository {
    TodayToDoList findTodayToDoListsByMemberId(final Long memberId);
}
