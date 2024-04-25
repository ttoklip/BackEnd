package com.api.ttoklip.domain.notification.event;

public record PostETCEvent(Long postId, String className, String methodName) {

    public static PostETCEvent of(Long postId, String className, String methodName) {
        return new PostETCEvent(postId, className, methodName);
    }
}
