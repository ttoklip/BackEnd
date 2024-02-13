package com.api.ttoklip.domain.manager.inquiry.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {
    @Query(value = "SELECT i FROM Inquiry i")
    Page<Inquiry> findByContentContaining(final Pageable pageable);
}
