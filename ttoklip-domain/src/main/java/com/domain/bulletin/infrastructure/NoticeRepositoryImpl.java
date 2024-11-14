package com.domain.bulletin.infrastructure;

import com.domain.bulletin.domain.Notice;
import com.domain.bulletin.domain.NoticeRepository;
import com.domain.bulletin.domain.NoticeResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository jpaRepository;
//    private final NoticeQueryRepository queryDSLRepository;

    @Override
    public Notice save(final Notice notice) {
        return jpaRepository.saveAndFlush(notice);
    }

    @Override
    public Notice findByIdActivated(final Long noticeId) {
//        return queryDSLRepository.findByIdActivated(noticeId);
        return null;
    }

    @Override
    public NoticeResponses getContain(final int pageNumber, final int pageSize) {
//        return queryDSLRepository.getContain(pageNumber, pageSize);
        return null;
    }

}
