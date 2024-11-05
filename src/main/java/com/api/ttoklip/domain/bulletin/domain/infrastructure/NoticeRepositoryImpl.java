package com.api.ttoklip.domain.bulletin.domain.infrastructure;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import com.api.ttoklip.domain.bulletin.domain.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository jpaRepository;
    private final NoticeQueryRepository queryDSLRepository;

    @Override
    public Notice save(final Notice notice) {
        return jpaRepository.saveAndFlush(notice);
    }

    @Override
    public Notice findByIdActivated(final Long noticeId) {
        return queryDSLRepository.findByIdActivated(noticeId);
    }

    @Override
    public Page<Notice> getContain(final Pageable pageable) {
        return queryDSLRepository.getContain(pageable);
    }

}
