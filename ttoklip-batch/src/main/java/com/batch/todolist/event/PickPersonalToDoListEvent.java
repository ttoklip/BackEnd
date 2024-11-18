package com.batch.todolist.event;

import com.batch.global.common.event.BatchEvent;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PickPersonalToDoListEvent extends BatchEvent {
    public PickPersonalToDoListEvent(final LocalDateTime eventTime) {
        super(eventTime);
    }
}
