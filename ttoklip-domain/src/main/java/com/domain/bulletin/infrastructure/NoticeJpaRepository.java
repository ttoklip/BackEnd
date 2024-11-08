package com.domain.bulletin.infrastructure;

import com.domain.bulletin.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {
}
