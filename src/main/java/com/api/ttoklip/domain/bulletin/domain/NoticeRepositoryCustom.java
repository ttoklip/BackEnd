package com.api.ttoklip.domain.bulletin.domain;

public interface NoticeRepositoryCustom {
    Notice findByIdActivated(final Long noticeId);

}
