package com.api.ttoklip.domain.mypage.noti.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
}
