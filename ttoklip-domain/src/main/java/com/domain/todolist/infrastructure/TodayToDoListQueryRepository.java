package com.domain.todolist.infrastructure;

import com.domain.todolist.domain.TodayToDoList;
import com.domain.todolist.domain.vo.ToDoList;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodayToDoListQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public TodayToDoList findTodayToDoListsByMemberId(final Long memberId) {
        QTodayToDoList todayToDoList = QTodayToDoList.todayToDoList;

        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        List<TodayToDoList> toDoLists = queryFactory
                .selectFrom(todayToDoList)
                .where(todayToDoList.member.id.eq(memberId)
                        .and(todayToDoList.createdDate.between(startOfDay, endOfDay)))
                .fetch();

        // 회원 가입 후 12시가 지나기 전, 오늘의 투두리스트가 없을 경우
        if (toDoLists.isEmpty()) {
            return generateToDoList();
        }

        // 그럴 가능성은 없지만, 혹시나 ToDoList가 2개 이상일 경우
        return toDoLists.get(0);
    }

    // ToDo 추후 사용처에서 회원 찾아서 넘기도록 수정
    private TodayToDoList generateToDoList() {
        ToDoList randomToDoList = ToDoList.getRandomToDoList();
        TodayToDoList newTodayToDoList = TodayToDoList.of(getCurrentMember(), randomToDoList);
        entityManager.persist(newTodayToDoList);
        return newTodayToDoList;
    }
}
