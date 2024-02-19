package com.api.ttoklip.domain.todolist.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayToDoListRepository extends JpaRepository<TodayToDoList, Long>, TodayToRoListCustomRepository {
}
