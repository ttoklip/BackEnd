package com.api.ttoklip.domain.mypage.noti.post.domain;

public interface NoticeRepositoryCustom {
    Notice findByIdActivated(final Long noticeId);

}
