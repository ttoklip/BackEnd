package com.api.ttoklip.domain.todolist.infrastructure;

import com.api.ttoklip.domain.todolist.domain.TodayToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayToDoListJpaRepository extends JpaRepository<TodayToDoList, Long> {

    TodayToDoList findTodayToDoListsByMemberId(Long memberId);
}
