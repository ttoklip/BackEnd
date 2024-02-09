package com.api.ttoklip.domain.mypage.noti.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom{
    @Query(value = "SELECT n FROM Notice n",
            countQuery = "SELECT COUNT(n) FROM Notice n")
    Page<Notice> findByContentContaining (final Pageable pageable);

}
