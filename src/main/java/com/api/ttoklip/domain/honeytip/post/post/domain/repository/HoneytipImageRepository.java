package com.api.ttoklip.domain.honeytip.post.post.domain.repository;

import com.api.ttoklip.domain.honeytip.post.post.domain.HoneytipImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoneytipImageRepository extends JpaRepository<HoneytipImage, Long> {
}
