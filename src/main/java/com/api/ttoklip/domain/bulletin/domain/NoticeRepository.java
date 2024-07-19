package com.api.ttoklip.domain.bulletin.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
}
