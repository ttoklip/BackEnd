package com.domain.todolist.infrastructure;

import com.domain.todolist.domain.TodayToDoList;
import com.domain.todolist.domain.TodayToDoListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodayToDoListRepositoryImpl implements TodayToDoListRepository {

    private final TodayToDoListJpaRepository jpaRepository;
    private final TodayToDoListQueryRepository queryDSLRepository;

    @Override
    public void saveAll(final List<TodayToDoList> todayToDoLists) {
        jpaRepository.saveAll(todayToDoLists);
    }

    @Override
    public TodayToDoList findTodayToDoListsByMemberId(final Long memberId) {
        return queryDSLRepository.findTodayToDoListsByMemberId(memberId);
    }
}
