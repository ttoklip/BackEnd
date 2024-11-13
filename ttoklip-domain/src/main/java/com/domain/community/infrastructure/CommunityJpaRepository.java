package com.domain.community.infrastructure;

import com.domain.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityJpaRepository extends JpaRepository<Community, Long> {

}
