package com.api.ttoklip.domain.honeytip.post.repository;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HoneyTipRepository extends JpaRepository<HoneyTip, Long>, HoneyTipRepositoryCustom {
    @Query(value = "SELECT h FROM HoneyTip h LEFT JOIN FETCH h.honeyTipComments WHERE h.content LIKE %:keyword%",
            countQuery = "SELECT count(h) FROM HoneyTip h WHERE h.content LIKE %:keyword%")
    Page<HoneyTip> findByContentContaining(final String keyword, final Pageable pageable);
}
