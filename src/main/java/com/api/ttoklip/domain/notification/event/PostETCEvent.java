package com.api.ttoklip.domain.notification.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PostETCEvent {

    private final Long postId;
    private final String className;
    private final String methodName;
}
