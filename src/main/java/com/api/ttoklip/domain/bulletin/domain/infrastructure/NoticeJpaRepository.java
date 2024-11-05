package com.api.ttoklip.domain.bulletin.domain.infrastructure;

import com.api.ttoklip.domain.bulletin.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {
}
