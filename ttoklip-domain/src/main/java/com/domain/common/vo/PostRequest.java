package com.domain.common.vo;

import com.common.Filterable;
import com.common.Lockable;

public interface PostRequest extends Lockable, Filterable {
    String getTitle();

    String getContent();

    default String getLockKey() {
        return getTitle() + getContent();
    }

    @Override
    default String getFilterContent() {
        return getTitle() + " " + getContent();
    }
}
