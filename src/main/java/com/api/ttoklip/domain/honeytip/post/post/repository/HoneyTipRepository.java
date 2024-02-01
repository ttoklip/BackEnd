package com.api.ttoklip.domain.honeytip.post.post.repository;

import com.api.ttoklip.domain.honeytip.post.post.domain.HoneyTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipRepository extends JpaRepository<HoneyTip, Long>, HoneyTipRepositoryCustom {

}
