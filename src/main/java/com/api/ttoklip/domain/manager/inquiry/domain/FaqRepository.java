package com.api.ttoklip.domain.manager.inquiry.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FaqRepository extends JpaRepository<Faq, Long>, FaqRepositoryCustom {
    @Query(value = "SELECT f FROM Faq f")
    Page<Faq> findByContentContaining(final Pageable pageable);
}
