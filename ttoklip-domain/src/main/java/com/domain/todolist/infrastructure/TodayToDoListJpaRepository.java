package com.domain.todolist.infrastructure;

import com.domain.todolist.domain.TodayToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayToDoListJpaRepository extends JpaRepository<TodayToDoList, Long> {

    TodayToDoList findTodayToDoListsByMemberId(Long memberId);
}
