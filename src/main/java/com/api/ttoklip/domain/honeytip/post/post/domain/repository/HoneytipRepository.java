package com.api.ttoklip.domain.honeytip.post.post.domain.repository;

import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HoneytipRepository extends JpaRepository<Honeytip, Long>, HoneytipRepositoryCustom {

}
