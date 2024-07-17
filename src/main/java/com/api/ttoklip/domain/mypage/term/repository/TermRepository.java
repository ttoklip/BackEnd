package com.api.ttoklip.domain.mypage.term.repository;

import com.api.ttoklip.domain.mypage.term.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
