package com.batch.newsletter.event;

import com.batch.global.common.event.BatchEvent;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PickRandomNewsletterEvent extends BatchEvent {

    public PickRandomNewsletterEvent(final LocalDateTime eventTime) {
        super(eventTime);
    }
}
