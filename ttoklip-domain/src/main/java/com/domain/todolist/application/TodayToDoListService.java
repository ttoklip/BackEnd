package com.domain.todolist.application;

import com.domain.todolist.domain.TodayToDoList;
import com.domain.todolist.domain.TodayToDoListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodayToDoListService {

    private final TodayToDoListRepository todayToDoListRepository;

    @Transactional
    public void saveAll(final List<TodayToDoList> todayToDoLists) {
        todayToDoListRepository.saveAll(todayToDoLists);
    }
}
