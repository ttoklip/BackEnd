package com.domain.member.domain.userInfo;

import com.common.base.Lockable;

public interface OAuth2UserInfo extends Lockable {
    String getProfile();

    String getName();

    String getEmail();

    default String getLockKey() {
        return getEmail();
    }
}
