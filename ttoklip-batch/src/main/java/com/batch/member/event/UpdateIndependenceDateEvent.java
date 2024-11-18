package com.batch.member.event;

import com.batch.global.common.event.BatchEvent;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateIndependenceDateEvent extends BatchEvent {

    public UpdateIndependenceDateEvent(final LocalDateTime eventTime) {
        super(eventTime);
    }
}
