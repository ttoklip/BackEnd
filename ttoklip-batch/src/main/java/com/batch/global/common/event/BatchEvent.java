package com.batch.global.common.event;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BatchEvent {

    private final LocalDateTime localDateTime;
}
