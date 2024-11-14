package com.domain.notification.domain.annotation;

import com.domain.notification.domain.vo.NotiCategory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SendCommentNotification {
    NotiCategory notiCategory();
}