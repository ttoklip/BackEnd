package com.api.ttoklip.domain.bulletin.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepository {
    Notice save(Notice notice);

    Notice findByIdActivated(final Long noticeId);

    Page<Notice> getContain(Pageable pageable);
}
