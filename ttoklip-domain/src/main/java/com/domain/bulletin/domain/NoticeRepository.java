package com.domain.bulletin.domain;


public interface NoticeRepository {
    Notice save(Notice notice);

    Notice findByIdActivated(Long noticeId);

    NoticeResponses getContain(int pageNumber, int pageSize);
}
