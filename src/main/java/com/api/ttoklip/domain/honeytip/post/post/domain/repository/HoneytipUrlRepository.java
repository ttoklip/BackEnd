package com.api.ttoklip.domain.honeytip.post.post.domain.repository;

import com.api.ttoklip.domain.honeytip.post.post.domain.HoneytipUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoneytipUrlRepository extends JpaRepository<HoneytipUrl, Long> {
}
