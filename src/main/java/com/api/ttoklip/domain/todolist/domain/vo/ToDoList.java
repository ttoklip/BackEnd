package com.api.ttoklip.domain.todolist.domain.vo;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ToDoList {
    WASH_BLANKET("오늘은 이불빨래 하는 날!"),
    THROW_AWAY_TRASH("오늘은 음쓰 버리는 날!"),
    RECYCLE("오늘은 재활용하는 날!"),
    CHILL_OUT("오늘은 기깔나게 쉬는 날!"),
    CLEAN_WASHING_MACHINE("오늘은 세탁기 청소하는 날!"),
    CLEAN_ENTRANCE("오늘은 현관 청소하는 날!"),
    CLEAN_STOVE_HOOD("오늘은 가스레인지 후드 청소하는 날!"),
    CLEAN_WINDOWS("오늘은 창문을 닦는 날!");

    private final String description;

    public String getDescription() {
        return description;
    }

    public static ToDoList getRandomToDoList() {
        List<ToDoList> valuesList = new java.util.ArrayList<>(List.of(ToDoList.values()));
        Collections.shuffle(valuesList);
        return valuesList.get(0);
    }
}
