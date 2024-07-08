package com.api.ttoklip.domain.honeytip.post.repository;

import com.api.ttoklip.domain.honeytip.post.domain.HoneyTip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoneyTipRepository extends JpaRepository<HoneyTip, Long>, HoneyTipRepositoryCustom {

}
